package com.miniorm.dao.reflex;

import java.util.HashMap;

/**
 * Created by admin on 2017-02-20.
 */

public class SqlCache {

    private  static HashMap<String ,String> xCache=new HashMap<String ,String>();

    public static  void  addSqlCache(String name,String reflexEntity){
        xCache.put(name,reflexEntity);
    }

    public static  String  getSqlCache(String name){
        return xCache.get(name);
    }


}
