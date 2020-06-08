package com.tangcz.springboot.mybatis.util;

import com.tangcz.springboot.mybatis.annotation.ShardStrategy;

public class DBContextHolder {
    private static final ThreadLocal<ShardStrategy> TL_SHARD_STRATEGY = new ThreadLocal<>();
    private static final ThreadLocal<Object>        TL_SHARD_VALUE    = new ThreadLocal<>();
    private static final ThreadLocal<Object[]>      TL_ARGS           = new ThreadLocal<>();
    private static final ThreadLocal<String>        TL_DATA_SOURCE_ID = new ThreadLocal<>();
    private static final ThreadLocal<Boolean>       TL_SHARDED        = new ThreadLocal<>();
    private static final ThreadLocal<String>        TL_SQL            = new ThreadLocal<>();

    public static boolean isSharded() {
        return TL_SHARDED.get() != null && TL_SHARDED.get();
    }

    public static void markSharded() {
        TL_SHARDED.set(true);
    }

    public static Object[] getArgs() {
        return TL_ARGS.get();
    }

    public static void setArgs(Object[] args) {
        TL_ARGS.set(args);
    }

    public static ShardStrategy getShardStrategy() {
        return TL_SHARD_STRATEGY.get();
    }

    public static void setShardStrategy(ShardStrategy shardStrategy) {
        TL_SHARD_STRATEGY.set(shardStrategy);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getShardValue() {
        return (T) TL_SHARD_VALUE.get();
    }

    public static void setShardValue(Object shardValue) {
        TL_SHARD_VALUE.set(shardValue);
    }

    public static String getDataSourceId() {
        return TL_DATA_SOURCE_ID.get();
    }

    public static void setDataSourceId(String dataSourceId) {
        TL_DATA_SOURCE_ID.set(dataSourceId);
    }

    public static String getSql() {
        return TL_SQL.get();
    }

    public static void setSql(String sql) {
        TL_SQL.set(sql);
    }

    public static void clear() {
        TL_SHARD_STRATEGY.remove();
        TL_SHARD_VALUE.remove();
        TL_ARGS.remove();
        TL_DATA_SOURCE_ID.remove();
        TL_SHARDED.remove();
        TL_SQL.remove();
    }

}
