package com.tom.redis.cache;

import com.tom.redis.pool.JedisPoolFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.Map;

/**
 * Set方法的Cache
 *
 * @author tomxin
 * @date 2018-11-11
 * @since v1.0.0
 */
public class SetCache {

    private static String INIT_CURSOR = "0";

    /**
     * 删除大数据量的Key
     *
     * @param key
     * @author tomxin
     * @date 2018-11-11
     */
    public void delBigSet(String key) {
        Jedis jedis = null;
        String cursor = INIT_CURSOR;
        try {
            jedis = JedisPoolFactory.getJedisPool().getResource();
            ScanParams scanParams = new ScanParams().count(100);
            do {
                ScanResult<String> scanResult = jedis.sscan(key, cursor, scanParams);
                for (String value : scanResult.getResult()) {
                    jedis.srem(key, value);
                }
                cursor = scanResult.getStringCursor();
            } while (!INIT_CURSOR.equals(cursor));
            // 删除Key值
            jedis.del(key);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }
}
