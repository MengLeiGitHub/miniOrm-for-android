package com.miniorm.query.map;


import com.miniorm.MiniOrm;
import com.miniorm.android.androidBaseDao;

import com.miniorm.dao.builder.Where;
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
import java.util.Objects;

/**
 * Created by admin on 2017-02-28.
 */

public class OneToManyMapping {

/*
    public List<? extends Object> proceedFilterToQuery(Object thisObject, Method  method) throws Exception {
        Class<?> targetClass=null;
        Class<?> returnclass=method.getReturnType();
        if(returnclass!=java.util.List.class&&returnclass!=java.util.ArrayList.class&&returnclass!=java.util.Collection.class){

            throw  new Exception("The framework supports only Collection or subclasses for many to many return types");
        }


        Type genericFieldType=method.getGenericReturnType();
        if(genericFieldType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            if(fieldArgTypes.length!=1){
                throw  new Exception("Do not have generics in the returned type");
            }
            targetClass=(Class<?>) fieldArgTypes[0];
        }

        final Class<?>  initClass=targetClass;


        androidBaseDao baseDao2=   new androidBaseDao<Object>(){

             @Override
             public Object getTableEntity() {
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
        for (String key : targetReflexForeignkeyColumnMap.keySet()) {
            TableColumnEntity tableColumnEntity = targetReflexForeignkeyColumnMap.get(key);
            field=tableColumnEntity.getField();
            if(field.getType()==thisObject.getClass()){
                query=    baseDao2.getTableEntity();
            }else {
                continue;
            }
        }

        if(field==null||query==null){
            throw     new Exception("请在"+thisObject.getClass().getName()+" 中 设置外键关联 "+targetClass.getName());
        }

        DebugLog.e(query.getClass().getName() +"  "+thisObject.getClass().getName()+"  "+field.getName());
        query=  new EntityParse<Object>((Class<Object>)query.getClass()).setEntityValue(query,thisObject,field);


        return  baseDao2.queryListByEntity(query);

    }
*/


    public List<? extends Object> proceedFilterToQuery(Object thisObject, Class<?>  targetClass, Class collectioin) throws Exception {

        if(collectioin!=java.util.List.class&&collectioin!=java.util.ArrayList.class&&collectioin!=java.util.Collection.class){

            throw  new Exception("The framework supports only Collection or subclasses for many to many return types");
        }


        final Class<?>  initClass=targetClass;



        /*androidBaseDao baseDao2=   new androidBaseDao<Object>(){

            @Override
            public Object getTableEntity() {
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
*/
        androidBaseDao baseDao2= MiniOrm.getTableDaoMapping().getDaoByName(initClass.getName()).newInstance();

        ReflexEntity targetReflex= ReflexCache.getReflexEntity(targetClass.getName());

        HashMap<String,TableColumnEntity> targetReflexForeignkeyColumnMap= targetReflex.getForeignkeyColumnMap();

        Object  query=null;
        Field field=null;
        String targetForeignKey=null;
        for (String key : targetReflexForeignkeyColumnMap.keySet()) {
            TableColumnEntity tableColumnEntity = targetReflexForeignkeyColumnMap.get(key);
            field=tableColumnEntity.getField();
            if(thisObject.getClass()==field.getType()||thisObject.getClass().getSuperclass()== field.getType()){
                targetForeignKey=tableColumnEntity.getColumnName();
            }else {
                continue;
            }
        }

        if(field==null){
            throw     new Exception("请在"+targetClass.getName()+" 中 设置外键关联 "+thisObject.getClass().getName());
        }

        //query=  new EntityParse<Object>((Class<Object>) query.getClass()).setEntityValue(query,thisObject,field);
        Field idFild=ReflexCache.getReflexEntity(thisObject.getClass().getName()).getTableIdEntity().getField();
        Object idval=  new EntityParse<>(null).getObjFromField(idFild,thisObject);

        return baseDao2.getQueryBuilder().callQuery().queryAll().where(Where.handle().eq(targetForeignKey, idval.toString())).executeQueryList();
    }



}
