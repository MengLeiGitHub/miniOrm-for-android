package com.miniorm.query.map;

import com.miniorm.android.KeyWork;
import com.miniorm.android.androidBaseDao2;
import com.miniorm.dao.BaseDao2;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.debug.DebugLog;
import com.miniorm.entity.TableColumnEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017-02-28.
 */

public class OneToManyMapping extends BaseMap<List<? extends Object>> {
    @Override
    public List<? extends Object> proceedFilterToQuery(Object thisObject, Method  method) throws Exception {
        Class<?> targetClass=null;
        Class<?> returnclass=method.getReturnType();
        if(returnclass!=java.util.List.class&&returnclass!=java.util.ArrayList.class&&returnclass!=java.util.Collection.class){

            throw  new Exception("框架多对多的返回类型只支持Collection或子类");
        }


        Type genericFieldType=method.getGenericReturnType();
        if(genericFieldType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            if(fieldArgTypes.length!=1){
                throw  new Exception("返回的类型中不要带有泛型");
            }
            targetClass=(Class<?>) fieldArgTypes[0];
        }

        final Class<?>  initClass=targetClass;


        androidBaseDao2  baseDao2=   new androidBaseDao2<Object>(){

             @Override
             public Object getQueryEntity() {
                 try {
                     return initClass.newInstance();
                 } catch (InstantiationException e) {
                     e.printStackTrace();
                 } catch (IllegalAccessException e) {
                     e.printStackTrace();
                 }
                 return null;
             }
         };

        ReflexEntity targetReflex= ReflexCache.getReflexEntity(targetClass.getName());

        HashMap<String,TableColumnEntity> targetReflexForeignkeyColumnMap= targetReflex.getForeignkeyColumnMap();

        Object  query=null;
        Field field=null;
        for (String key : targetReflexForeignkeyColumnMap.keySet()) {//一TO多  1
            TableColumnEntity tableColumnEntity = targetReflexForeignkeyColumnMap.get(key);
            field=tableColumnEntity.getField();
            if(field.getType()==thisObject.getClass()){
                query=    baseDao2.getQueryEntity();
            }else {
                continue;
            }
        }

        if(field==null||query==null){
            throw     new Exception("请在"+thisObject.getClass().getName()+" 中 设置外键关联 "+targetClass.getName());
        }

        DebugLog.e(query.getClass().getName() +"  "+thisObject.getClass().getName()+"  "+field.getName());
        query=  new EntityParse<Object>(query).setEntityValue(query,thisObject,field);


        return  baseDao2.queryListByEntity(query);

    }
}
