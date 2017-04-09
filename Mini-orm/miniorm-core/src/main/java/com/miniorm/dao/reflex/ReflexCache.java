package com.miniorm.dao.reflex;

import android.util.Log;

import com.miniorm.dao.utils.Content;

import java.util.HashMap;

/**
 * Created by admin on 2016/9/29.
 */
public class ReflexCache {

    private  static HashMap<String ,ReflexEntity>  xCache=new HashMap<String ,ReflexEntity>();

    public static  void  addReflexEntity(String name,ReflexEntity reflexEntity){
        Log.e("tag",name+"  "+reflexEntity.getTableName());
        xCache.put(name,reflexEntity);
    }

    public static ReflexEntity  getReflexEntity(String name){
        if(name.endsWith(Content.NEW_CLASS_NAME)){
            return xCache.get(name);
        }
        String returnClassNameProxy= ProxyCache.getProxyClass(name).getName();
        return xCache.get(returnClassNameProxy);
    }
}
