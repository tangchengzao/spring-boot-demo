package com.tangcz.springboot.mybatis.datasource;

import com.tangcz.springboot.common.module.config.pojo.DataSourceDO;
import com.tangcz.springboot.common.util.ReflectionUtil;
import com.tangcz.springboot.mybatis.JdbcConfig;
import com.tangcz.springboot.mybatis.util.DBContextHolder;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class DynamicDataSource extends AbstractRoutingDataSource {
    public static final String  CONFIG_DATA_SOURCE = "APIUNION-CONFIG";

    private static final Logger LOGGER             = LoggerFactory.getLogger(DynamicDataSource.class);

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSource);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String dsId = DBContextHolder.getDataSourceId();
        if (StringUtils.isBlank(dsId)) {
            return CONFIG_DATA_SOURCE;
        }
        return dsId;
    }

    @SuppressWarnings("unchecked")
    public void refreshDataSource(List<DataSourceDO> dataSourceList) {
        try {
            Field fdTargetDataSources = ReflectionUtil.findField(AbstractRoutingDataSource.class, "targetDataSources");
            fdTargetDataSources.setAccessible(true);
            Map<Object, Object> currentTargetDataSource = (Map<Object, Object>) fdTargetDataSources.get(this);
            Object configDS = currentTargetDataSource.get(DynamicDataSource.CONFIG_DATA_SOURCE);
            
            Map<Object, Object> newTargetDataSource = new LinkedHashMap<>();
            newTargetDataSource.put(DynamicDataSource.CONFIG_DATA_SOURCE, configDS);
            
            for (DataSourceDO dataSourceDO : dataSourceList) {
                String key = String.valueOf(dataSourceDO.getId());
                Object existedDataSource = currentTargetDataSource.get(key);
                if (existedDataSource == null) {
                    JdbcConfig jdbcConfig = dataSourceDO.parseDataSource();
                    HikariDataSource ds = new HikariDataSource();
                    ds.setJdbcUrl(jdbcConfig.getJdbcUrl());
                    ds.setUsername(jdbcConfig.getUserName());
                    ds.setPassword(jdbcConfig.getPassword());
                    ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
                    ds.setPoolName(String.format("hikari-pool-datasource-%s", dataSourceDO.getName()));
                    ds.setConnectionTimeout(30_000);
                    ds.setIdleTimeout(60_000);
                    ds.setMaxLifetime(3600_000);
                    ds.setMaximumPoolSize(50);
                    ds.setMinimumIdle(5);
                    ds.setAutoCommit(true);
                    ds.setValidationTimeout(10_000);
                    ds.setConnectionTestQuery("select 1 from dual");
                    
                    newTargetDataSource.put(key, ds);
                } else {
                    newTargetDataSource.put(key, existedDataSource);
                }
            }
            
            try {
                fdTargetDataSources.set(this, newTargetDataSource);
                this.afterPropertiesSet();
            } catch (Exception e) {
                LOGGER.error("refresh data source at switch step failed ", e);
            }

            // 关闭无用的连接
            Set<Object> currentKeys = new HashSet<>(currentTargetDataSource.keySet());
            currentKeys.removeAll(newTargetDataSource.keySet());
            for (Object key : currentKeys) {
                try {
                    Closeable ds = (Closeable) currentTargetDataSource.get(key);
                    LOGGER.warn(String.format("data source %s closed", String.valueOf(key)));
                    ds.close();
                } catch (IOException ignore) {
                }
            }
        } catch (Exception e) {
            LOGGER.error("refresh data source failed ", e);
        }
    }
}
