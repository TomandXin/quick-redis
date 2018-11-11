package com.tom.redis.cache;

import com.tom.redis.pool.JedisPoolFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.util.List;

/**
 * SortSet
 *
 * @author tomxin
 * @date 2018-11-11
 * @since v1.0.0
 */
public class SortSetCache {

    private static String INIT_CURSOR = "0";

    /**
     * 删除大的SortSet
     *
     * @param key
     * @author tomxin
     * @date 2018-11-11
     */
    public void delBigSortSet(String key) {
        Jedis jedis = null;
        String cursor = INIT_CURSOR;
        try {
            jedis = JedisPoolFactory.getJedisPool().getResource();
            ScanParams scanParams = new ScanParams().count(100);
            do {
                ScanResult<Tuple> scanResult = jedis.zscan(key, cursor, scanParams);

                List<Tuple> tupleList = scanResult.getResult();
                // 循环删除单个元素
                for (Tuple tuple : tupleList) {
                    jedis.zrem(key, tuple.getElement());
                }
                cursor = scanResult.getStringCursor();
            } while (!INIT_CURSOR.equals(cursor));

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }
}
