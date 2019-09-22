package com.miniorm.dao.reflex;

import android.util.Log;

import com.miniorm.MiniOrm;
import com.miniorm.dao.utils.Content;
import com.miniorm.debug.DebugLog;

import java.util.HashMap;

/**
 * Created by admin on 2016/9/29.
 */
public class ReflexCache {

    private  static HashMap<String ,ReflexEntity>  xCache=new HashMap<String ,ReflexEntity>();

    public static  void  addReflexEntity(String name,ReflexEntity reflexEntity){
        DebugLog.e(name+" ---> "+reflexEntity.getTableName());
        xCache.put(name,reflexEntity);
    }

    public static ReflexEntity  getReflexEntity(String name){
        if(name.endsWith(Content.NEW_CLASS_NAME)){
            return xCache.get(name)==null?xCache.get(name.replace(Content.NEW_CLASS_NAME,"")):xCache.get(name);
        }
        try {
            Class cls=  MiniOrm.getTableDaoMapping().getProxyClass(name);
            if (cls == null) {
                String returnClassNameProxy= ProxyCache.getProxyClass(name).getName();
                return xCache.get(returnClassNameProxy);
            }
            return xCache.get(cls.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return  xCache.get(name);
    }
}
