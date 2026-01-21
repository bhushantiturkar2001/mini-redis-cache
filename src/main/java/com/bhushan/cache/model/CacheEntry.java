package com.bhushan.cache.model;

public class CacheEntry<K, V> {

    // Cache entry
    private final K key;
    private V value;
    private final long expiryTime; // epoch mille

    public CacheEntry(K key, V value, long ttlMills){
        this.key = key;
        this.value = value;
        this.expiryTime = ttlMills > 0
                ? System.currentTimeMillis() + ttlMills
                :-1; // -1 means no expiry
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
    public void setValue(V value){
        this.value = value;
    }

    public boolean isExpired(){
        return  expiryTime != -1 && System.currentTimeMillis() > expiryTime;
    }
}
