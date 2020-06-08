package com.tangcz.springboot.mybatis.shard.converter;

import com.tangcz.springboot.common.util.ScriptEngine;
import com.tangcz.springboot.mybatis.annotation.ShardStrategy;
import com.tangcz.springboot.mybatis.util.DBContextHolder;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:ScriptShardSqlConverter
 * Package:com.tangcz.springboot.mybatis.shard.converter
 * Description:
 *
 * @date:2020/6/8 1:23
 * @author:tangchengzao
 */
public class ScriptShardSqlConverter<TShardValue> implements ShardSqlConverter<TShardValue> {
    @Override
    public String convert(String originalSql, Object[] args, TShardValue shardValue) {
        ShardStrategy shardStrategy = DBContextHolder.getShardStrategy();
        if (shardStrategy == null || StringUtils.isBlank(shardStrategy.script())) {
            return originalSql;
        }

        Map<String, Object> ctx = new HashMap<>();
        ctx.put("args", args);
        ctx.put("sql", originalSql);
        ctx.put("shardValue", shardValue);

        return ScriptEngine.evalToString(shardStrategy.scriptLang(), shardStrategy.script(), ctx, null);
    }
}
