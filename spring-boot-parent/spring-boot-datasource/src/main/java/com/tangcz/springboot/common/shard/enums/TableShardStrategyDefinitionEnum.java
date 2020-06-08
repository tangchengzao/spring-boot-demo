package com.tangcz.springboot.common.shard.enums;

import com.tangcz.springboot.common.shard.strategy.TableShardStrategy;

/**
 * ClassName:TableShardStrategyDefinitionEnum
 * Package:com.tangcz.springboot.common.shard.enums
 * Description:
 *
 * @date:2020/6/8 1:18
 * @author:tangchengzao
 */
public enum TableShardStrategyDefinitionEnum {

    ;

    private String             tableName;
    private TableShardStrategy shardStrategy;
    private String             baseTableName;
    // 表名占位符
    private String             tableNamePlaceHolder;

    TableShardStrategyDefinitionEnum(String tableName, TableShardStrategy shardStrategy) {
        this(tableName, tableName + "_0", "$" + tableName + "$", shardStrategy);
    }

    TableShardStrategyDefinitionEnum(String tableName, String baseTableName, String tableNamePlaceHolder, TableShardStrategy shardStrategy) {
        this.tableName = tableName;
        this.baseTableName = baseTableName;
        this.shardStrategy = shardStrategy;
        this.tableNamePlaceHolder = tableNamePlaceHolder;
    }

    public String getTableNamePlaceHolder() {
        return tableNamePlaceHolder;
    }

    public String getBaseTableName() {
        return baseTableName;
    }

    public String getTableName() {
        return tableName;
    }

    public TableShardStrategy getShardStrategy() {
        return shardStrategy;
    }

}
