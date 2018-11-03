package com.tom.redis.util;

import java.util.List;

/**
 *
 * @author sanjin
 * @date 2018-11-03
 * @since v1.0.0
 */
public class CollectionUtils {

    public static boolean isEmpty(List<?> sourceList) {
        return null == sourceList || sourceList.size() == 0;
    }
}
