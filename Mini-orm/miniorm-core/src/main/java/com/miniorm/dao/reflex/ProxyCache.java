package com.miniorm.dao.reflex;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by admin on 2017-04-04.
 */

public class ProxyCache {

    private  static HashMap<String ,Class> xCache=new LinkedHashMap<>();

    public static  void  addProxyClass(String name,Class proclass){
        xCache.put(name,proclass);
    }

    public static boolean isHaveProxy(String name){
        return xCache.containsKey(name);
    }

    public static Class  getProxyClass(String name){
        return xCache.get(name);
    }

}
