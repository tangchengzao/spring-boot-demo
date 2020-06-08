package com.tangcz.springboot.mybatisplus.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.tangcz.springboot.common.util.CollectionUtil;
import com.tangcz.springboot.mybatisplus.parser.DynamicTableNamePrefixParser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:TableNameConfig
 * Package:com.tangcz.springboot.mybatisplus.config
 * Description:
 *
 * @date:2020/6/6 19:36
 * @author:tangchengzao
 */
@Configuration
@MapperScan("com.tangcz.springboot.common.module.*.dao")
public class TableNameConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        DynamicTableNamePrefixParser dynamicTableNamePrefixParser = new DynamicTableNamePrefixParser();
        paginationInterceptor.setSqlParserList(CollectionUtil.asArrayList(dynamicTableNamePrefixParser));
        return paginationInterceptor;
    }

}
