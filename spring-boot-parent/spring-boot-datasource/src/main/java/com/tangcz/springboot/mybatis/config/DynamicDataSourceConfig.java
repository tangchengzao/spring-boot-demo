package com.tangcz.springboot.mybatis.config;

import com.tangcz.springboot.mybatis.datasource.DynamicDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:DynamicDataSourceConfig
 * Package:com.tangcz.springboot.mybatis.config
 * Description:
 *
 * @date:2020/6/6 15:56
 * @author:tangchengzao
 */
@Configuration
public class DynamicDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource configDataSource(DataSourceProperties dataSourceProperties) {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource configDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSource.CONFIG_DATA_SOURCE, configDataSource);
        return new DynamicDataSource(configDataSource, targetDataSources);
    }

}
