package com.tom.redis.cache;

import com.tom.redis.pool.JedisPoolFactory;
import redis.clients.jedis.Jedis;

/**
 * List
 *
 * @author tomxin
 * @date 2018-11-11
 * @since v1.0.0
 */
public class ListCache {

    /**
     * 删除长度比较长的Key
     *
     * @param key
     * @param limit
     */
    public void delBigList(String key, int limit) {
        Jedis jedis = null;
        if (limit <= 0) {
            return;
        }
        int index = 0;
        try {
            jedis = JedisPoolFactory.getJedisPool().getResource();
            // 获取长度
            long length = jedis.llen(key);
            while (index < length) {
                jedis.ltrim(key, index, limit);
                index += limit;
            }
            jedis.del(key);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
    }
}
