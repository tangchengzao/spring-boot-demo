package com.tangcz.springboot.common.module.config.util;

import com.tangcz.springboot.common.module.config.dao.DataSourceMapper;
import com.tangcz.springboot.common.module.config.pojo.DataSourceDO;
import com.tangcz.springboot.common.spring.util.SpringBeanUtil;
import com.tangcz.springboot.common.util.CollectionUtil;
import com.tangcz.springboot.mybatis.datasource.DynamicDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ClassName:DataSourceRouteUtil
 * Package:com.tangcz.springboot.common.module.config.util
 * Description:
 *
 * @date:2020/6/6 14:54
 * @author:tangchengzao
 */
@Component
@DependsOn({"springBeanUtil", "dynamicDataSourceConfig", "paginationInterceptor"})
public class DataSourceRouteUtil implements InitializingBean {

    private static Map<Long, DataSourceDO> dataSourceMap = new LinkedHashMap<>();
    // 最后更新时间
    private static long                    latestUpdateTime;
    // 多线程控制
    private static AtomicBoolean           processing    = new AtomicBoolean();

    @Override
    public void afterPropertiesSet() throws Exception {
        reloadIfNecessary(true);
    }

    private static void reloadIfNecessary(boolean force) {
        long time = getNextUpdateTimeStamp();
        if (force || time > latestUpdateTime) {
            if (processing.compareAndSet(false, true)) {
                try {
                    DataSourceMapper dataSourceMapper = SpringBeanUtil.getBean(DataSourceMapper.class);
                    List<DataSourceDO> dataSourceList = dataSourceMapper.selectAll();
                    dataSourceMap = CollectionUtil.toMap(dataSourceList, "id");

                    DynamicDataSource dynamicDataSource = SpringBeanUtil.getBean(DynamicDataSource.class);
                    dynamicDataSource.refreshDataSource(dataSourceList);
                } finally {
                    latestUpdateTime = time;
                    processing.set(false);
                }
            }
        }
    }

    private static long getNextUpdateTimeStamp() {
        return (System.currentTimeMillis() / 600_000 + 1) * 600_000;
    }
}
