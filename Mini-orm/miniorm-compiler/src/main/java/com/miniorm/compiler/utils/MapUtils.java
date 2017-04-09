package com.miniorm.compiler.utils;

import java.util.Map;

/**
 * Created by admin on 2017-03-28.
 */

public class MapUtils {

    public static boolean isEmpty(final Map<?,?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(final Map<?,?> map) {
        return !MapUtils.isEmpty(map);
    }
}
