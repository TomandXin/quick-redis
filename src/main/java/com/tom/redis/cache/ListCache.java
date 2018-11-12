package com.tom.redis.cache;

import com.tom.redis.pool.JedisPoolFactory;
import redis.clients.jedis.Jedis;

import java.util.List;

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

    /**
     * lPush
     *
     * @param key
     * @param value
     * @return
     */
    public Long lPush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolFactory.getJedisPool().getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    public Long rPush(String key, String[] values) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolFactory.getJedisPool().getResource();
            return jedis.rpush(key, values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * lPop
     *
     * @param key
     * @return
     */
    public String lPop(String key) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolFactory.getJedisPool().getResource();
            return jedis.lpop(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * blPop
     *
     * @param timeout
     * @param key
     * @return
     */
    public List<String> blPop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolFactory.getJedisPool().getResource();
            return jedis.blpop(timeout, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }
}
