package com.tangcz.springboot.common.shard.strategy;

/**
 * ClassName:TableShardStrategy
 * Package:com.tangcz.springboot.common.shard.strategy
 * Description:
 *
 * @date:2020/6/8 1:19
 * @author:tangchengzao
 */
public interface TableShardStrategy<T> {

    public String getShardSuffix(T shardParam);

}
