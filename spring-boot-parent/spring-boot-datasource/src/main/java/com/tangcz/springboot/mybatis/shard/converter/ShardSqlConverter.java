package com.tangcz.springboot.mybatis.shard.converter;

/**
 * ClassName:ShardSqlConverter
 * Package:com.tangcz.springboot.mybatis.shard.converter
 * Description:
 *
 * @date:2020/6/8 1:15
 * @author:tangchengzao
 */
public interface ShardSqlConverter<TShardValue> {
    String convert(String originalSql, Object[] args, TShardValue shardValue);
}
