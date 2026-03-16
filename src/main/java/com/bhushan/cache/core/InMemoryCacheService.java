package com.bhushan.cache.core;

import com.bhushan.cache.model.CacheEntry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InMemoryCacheService<K, V> implements CacheService<K, V> {

//    private final Map<K, CacheEntry<K, V>> cache = new HashMap<>();

    private final Map<K, CacheEntry<K, V>> cache = new HashMap<>();

    @Override
    public void put(K key, V value) {
        CacheEntry<K, V> entry = new CacheEntry<>(key, value, -1);
        cache.put(key, entry);
    }

    @Override
    public void put(K key, V value, long ttlMillis) {
        CacheEntry<K, V> entry = new CacheEntry<>(key, value, ttlMillis);
        cache.put(key, entry);
    }

    @Override
    public V get(K key) {
        CacheEntry<K, V> entry = cache.get(key);

        if (entry == null) {
            return null;
        }

        if (entry.isExpired()) {
            cache.remove(key);
            return null;
        }

        return entry.getValue();
    }


    @Override
    public void delete(K key) {
        cache.remove(key);
    }


    public void cleanupExpiredEntries() {
        Iterator<Map.Entry<K, CacheEntry<K, V>>> iterator = cache.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<K, CacheEntry<K, V>> mapEntry = iterator.next();

            if (mapEntry.getValue().isExpired()) {
                iterator.remove();
            }
        }
    }

    public InMemoryCacheService() {

        Thread cleanupThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    cleanupExpiredEntries();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        cleanupThread.setDaemon(true);
        cleanupThread.start();
    }
}
