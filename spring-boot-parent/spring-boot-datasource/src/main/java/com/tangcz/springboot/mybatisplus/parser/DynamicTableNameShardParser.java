package com.tangcz.springboot.mybatisplus.parser;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.SqlInfo;
import com.tangcz.springboot.mybatis.annotation.ShardStrategy;
import com.tangcz.springboot.mybatis.shard.converter.ScriptShardSqlConverter;
import com.tangcz.springboot.mybatis.shard.converter.ShardSqlConverter;
import com.tangcz.springboot.mybatis.shard.converter.ShardStrategySqlConverter;
import com.tangcz.springboot.mybatis.util.DBContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ClassName:DynamicTableNameShardParser
 * Package:com.tangcz.springboot.mybatisplus.parser
 * Description:
 *
 * @date:2020/6/8 0:51
 * @author:tangchengzao
 */
public class DynamicTableNameShardParser implements ISqlParser {

    private static final ConcurrentMap<Class<?>, ShardSqlConverter> CVT_MAP = new ConcurrentHashMap<>();

    @Override
    public SqlInfo parser(MetaObject metaObject, String sql) {
        ShardStrategy shardStrategy = DBContextHolder.getShardStrategy();
        if (shardStrategy != null) {
            Class<? extends ShardSqlConverter> sqlConverterClass;
            if (StringUtils.isNotBlank(shardStrategy.script())) {
                sqlConverterClass = ScriptShardSqlConverter.class;
            } else {
                sqlConverterClass = ShardStrategySqlConverter.class;
            }


        }
        return null;
    }
}
