package com.test.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * google guava
 *
 * @author lz-119612
 * @version 1.0
 * @date 2019/12/20 15:48
 **/
public class GuavaCacheTest {

    private static LoadingCache<String, String> loadingCache;
    public static void main(String[] args) throws ExecutionException {
        loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .initialCapacity(3)
                .maximumSize(3)
                .concurrencyLevel(1)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        System.out.println("come in guava cache");
                        return "value";
                    }
                });

        System.out.println("----------"+loadingCache.get("test")+"---------"+loadingCache.get("test"));
    }


}
