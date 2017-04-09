package com.miniorm.query.map;

import java.lang.reflect.Method;

/**
 * Created by admin on 2017-02-28.
 */

public abstract class BaseMap<T> {


    public abstract    T   proceedFilterToQuery(Object thisObject, Method method) throws Exception;



}
