package com.miniorm.dao.utils;

import java.util.HashMap;

/**
 * Created by admin on 2016/9/29.
 */
public class ReflexCache {

    private  static HashMap<String ,ReflexEntity>  xCache=new HashMap<String ,ReflexEntity>();

    public static  void  addReflexEntity(String name,ReflexEntity reflexEntity){
        xCache.put(name,reflexEntity);
    }

    public static ReflexEntity  getReflexEntity(String name){
        return xCache.get(name);
    }


}
