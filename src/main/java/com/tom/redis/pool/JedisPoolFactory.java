package com.tom.redis.pool;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis Pool Factory
 *
 * @author tomxin
 * @date 2018-11-04
 * @since v1.0.0
 */
public class JedisPoolFactory {

    private static volatile JedisPool jedisPool;

    private static Long MAX_WAIT_MILLIS = 1000 * 60L;

    public static JedisPool getJedisPool() {
        if (null == jedisPool) {
            synchronized (JedisPoolFactory.class) {
                if (null == jedisPool) {
                    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
                    // 控制一个pool最多有多少个状态为idle(空闲)的jedis实例
                    jedisPoolConfig.setMaxIdle(16);
                    // 控制最大的jedis实例
                    jedisPoolConfig.setMaxTotal(128);
                    // 控制最大
                    jedisPoolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS);

                    jedisPoolConfig.setTestOnBorrow(true);

                    jedisPool = new JedisPool(jedisPoolConfig, "101.132.109.136", 6379);
                }
            }
        }
        return jedisPool;
    }
}
