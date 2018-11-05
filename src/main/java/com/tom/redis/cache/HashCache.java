package com.tom.redis.cache;

import com.tom.redis.pool.JedisPoolFactory;
import com.tom.redis.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Map;

/**
 * Hash Cache
 *
 * @author tomxin
 * @date 2018-11-03
 * @since v1.0.0
 */
public class HashCache {

    private static String SCAN_START = "0";

    /**
     * Hash对象的删除 hscan + hdel
     *
     * @param host
     * @param port
     * @param password
     * @param bigHashKey
     */
    public void delBigHash(String host, int port, String password, String bigHashKey) {
        Jedis jedis = null;
        String cursor = SCAN_START;
        ScanParams scanParams = new ScanParams().count(100);
        try {
            jedis = JedisPoolFactory.getJedisPool().getResource();
            do {
                ScanResult<Map.Entry<String, String>> scanResult = jedis.hscan(bigHashKey, cursor, scanParams);
                List<Map.Entry<String, String>> entryList = scanResult.getResult();
                if (!CollectionUtils.isEmpty(entryList)) {
                    for (Map.Entry<String, String> entry : entryList) {
                        jedis.hdel(bigHashKey, entry.getKey());
                    }
                }
                cursor = scanResult.getStringCursor();
            } while (!SCAN_START.equals(cursor));
            // 删除
            jedis.del(bigHashKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

}
