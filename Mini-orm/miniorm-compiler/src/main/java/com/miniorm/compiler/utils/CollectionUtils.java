package com.miniorm.compiler.utils;

import java.util.Collection;

/**
 * Created by admin on 2017-03-27.
 */

public class CollectionUtils {

    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean notEmpty(final Collection<?> coll){
        return !isEmpty(coll);
    }

}
