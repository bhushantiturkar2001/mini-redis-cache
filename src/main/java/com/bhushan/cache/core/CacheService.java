package com.bhushan.cache.core;

public interface CacheService<K, V> {

    void put(K key, V value);

    void put(K key, V value, long ttlMillis);

    V get(K key);

    void delete(K key);
}
