package com.tangcz.springboot.mybatis.shard.converter;

import com.tangcz.springboot.common.shard.enums.TableShardStrategyDefinitionEnum;
import com.tangcz.springboot.common.util.CollectionUtil;
import com.tangcz.springboot.mybatis.annotation.ShardStrategy;
import com.tangcz.springboot.mybatis.util.DBContextHolder;
import org.apache.commons.lang3.StringUtils;

/**
 * ClassName:ShardStrategySqlConverter
 * Package:com.tangcz.springboot.mybatis.shard.converter
 * Description:
 *
 * @date:2020/6/8 1:36
 * @author:tangchengzao
 */
public class ShardStrategySqlConverter<TShardValue> implements ShardSqlConverter<TShardValue> {
    @Override
    public String convert(String originalSql, Object[] args, TShardValue shardValue) {
        ShardStrategy shardStrategy = DBContextHolder.getShardStrategy();
        if (shardStrategy == null || CollectionUtil.isEmpty(shardStrategy.value())) {
            return originalSql;
        }

        String sql = originalSql;
        for (TableShardStrategyDefinitionEnum strategy : shardStrategy.value()) {
            sql = StringUtils.replace(sql, strategy.getTableNamePlaceHolder(),
                    strategy.getTableName() + "_" + strategy.getShardStrategy());
        }
        return null;
    }
}
