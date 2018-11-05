package com.tom.redis.cache;

import com.tom.redis.pool.JedisPoolFactory;
import redis.clients.jedis.Jedis;

/**
 * @author tomxin
 * @date 2018-11-03
 * @since v1.0.0
 */
public class StringCache {

    /**
     * 分布式锁setNx
     *
     * @param key
     * @param timeout
     * @return
     */
    public static Boolean setNx(String key, Long timeout) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolFactory.getJedisPool().getResource();
            Long result = jedis.setnx(key, String.valueOf(timeout));
            if (result > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * 存储一个Value值
     *
     * @param key
     * @param value
     * @return
     */
    public static String putValue(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolFactory.getJedisPool().getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * 删除对应的字符串值
     *
     * @param key
     * @return
     */
    public static boolean delValue(String key) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolFactory.getJedisPool().getResource();
            Long count = jedis.del(key);
            if (count > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }
}
