package com.bhushan.cache;

import com.bhushan.cache.model.CacheEntry;

public class Main {
    public static void main(String[] args) {
        System.out.println("Mini Redis Cache Engine Started...");

        CacheEntry<String, String> entry =
                new CacheEntry<>("name", "Bhushan", 2000);

        System.out.println(entry.getValue());

    }
}
