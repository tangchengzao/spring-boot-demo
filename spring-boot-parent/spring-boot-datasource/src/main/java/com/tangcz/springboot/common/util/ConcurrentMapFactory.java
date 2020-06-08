package com.tangcz.springboot.common.util;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.EvictionListener;

import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapFactory {

    public static <K, V> ConcurrentMap<K, V> getLRUConcurrentMap(int capacity) {
        return new ConcurrentLinkedHashMap.Builder<K, V>().maximumWeightedCapacity(capacity).build();
    }

    public static <K, V> ConcurrentMap<K, V> getLRUConcurrentMap(int capacity, EvictionListener<K, V> listener) {
        return new ConcurrentLinkedHashMap.Builder<K, V>().maximumWeightedCapacity(capacity).listener(listener).build();
    }

}
