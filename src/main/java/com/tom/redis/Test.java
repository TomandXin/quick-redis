package com.tom.redis;

import com.tom.redis.cache.HashCache;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("key_1", "value_1");
        valueMap.put("key_2", "value_2");
        HashCache.hSet("hash_map_key", valueMap);

        System.out.println(HashCache.hLen("hash_map_key"));
    }
}
