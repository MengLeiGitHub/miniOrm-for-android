package com.miniorm.query.map;

import com.miniorm.android.KeyWork;
import com.miniorm.android.androidBaseDao2;
import com.miniorm.annotation.ManyToMany;
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

public class ManyToManyMapping extends BaseMap<List<? extends Object>> {
    @Override
    public List<? extends Object> proceedFilterToQuery(Object thisObject, Method  method) throws Exception {
        Class<?> targetClass=null;
        Class<?> returnclass=method.getReturnType();
        if(returnclass!=List.class&&returnclass!=java.util.ArrayList.class&&returnclass!=java.util.Collection.class){

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

        final Class<?>  initClass=targetClass;//表示 返回的 类的 class

        ManyToMany  manyToMany=   method.getAnnotation(ManyToMany.class);
        final Class<?>    bridgingTableClass=manyToMany.bridgingTable();//中间表的 class




        androidBaseDao2  returnClassDao=   new androidBaseDao2<Object>(){

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
        ReflexEntity  bridgingTableReflexEntity=ReflexCache.getReflexEntity(bridgingTableClass.getName());
        if(bridgingTableReflexEntity==null){
            EntityParse entityParse = new EntityParse(bridgingTableClass);
            Object bridging= bridgingTableClass.newInstance();
            bridgingTableReflexEntity=entityParse.getFieldValueFromT(bridging);
            ReflexCache.addReflexEntity(bridgingTableClass.getName(), bridgingTableReflexEntity);
        }


        String    thisObjectBridgingColumnName=null;

        String   returnObjectBridgingIdColumnName=null;

        StringBuilder bridgingTableSql=new StringBuilder();
        HashMap<String,TableColumnEntity> bridgingTableReflexEntityTableColumnMap= bridgingTableReflexEntity.getTableColumnMap();
        Field  thisObjectbridgingTableIDField=null;
        Field field=null;
        for (String key : bridgingTableReflexEntityTableColumnMap.keySet()) {//一TO多  1
            TableColumnEntity tableColumnEntity = bridgingTableReflexEntityTableColumnMap.get(key);
            field=tableColumnEntity.getField();
            if(field.getType()==thisObject.getClass()){
                thisObjectBridgingColumnName=tableColumnEntity.getColumnName();
                thisObjectbridgingTableIDField=field;

            }else  if(field.getType()==initClass){
                returnObjectBridgingIdColumnName=tableColumnEntity.getColumnName();

            }else {
                continue;
            }
        }
        Object  thisObjectIdVal=null;
        {//获取当前类中的id的值
            Field  thisObjectIDField=  ReflexCache.getReflexEntity(thisObject.getClass().getName()).getTableIdEntity().getField();
            thisObjectIDField.setAccessible(true);
            thisObjectIdVal=thisObjectIDField.get(thisObject);
        }



        bridgingTableSql.append(KeyWork.SELECT);

        bridgingTableSql.append(returnObjectBridgingIdColumnName);


        bridgingTableSql.append(KeyWork.FROM);


        bridgingTableSql.append(bridgingTableReflexEntity.getTableName());


        bridgingTableSql.append(KeyWork.WHERE);

        bridgingTableSql.append(thisObjectBridgingColumnName);
        bridgingTableSql.append(KeyWork.Eq);
        if(thisObjectbridgingTableIDField.getType()==String.class){
            bridgingTableSql.append("'");
            bridgingTableSql.append(thisObjectIdVal.toString());
            bridgingTableSql.append("'");
        }else {
            bridgingTableSql.append(thisObjectIdVal.toString());
        }



       ReflexEntity targetReflex= ReflexCache.getReflexEntity(targetClass.getName());

        StringBuilder targetSql=new StringBuilder();

        targetSql.append(KeyWork.SELECT);

        targetSql.append(KeyWork.ALL);

        targetSql.append(KeyWork.FROM);
        targetSql.append(targetReflex.getTableName());
        targetSql.append(KeyWork.WHERE);

        targetSql.append(targetReflex.getTableIdEntity().getColumnName());
        targetSql.append(KeyWork.IN);

        targetSql.append("(");
        targetSql.append("\n");
        targetSql.append(bridgingTableSql);
        targetSql.append("\n");
        targetSql.append(")");


        return  returnClassDao.executeQueryList(targetSql.toString());



    }































 /*  androidBaseDao2  bridgingClassDao=   new androidBaseDao2<Object>(){

            @Override
            public Object getQueryEntity() {
                try {
                    return bridgingTableClass.newInstance();
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
        if(targetReflexForeignkeyColumnMap.size()!=0){

        }
        Object  query=null;
        Field field=null;
        for (String key : targetReflexForeignkeyColumnMap.keySet()) {//一TO多  1
            TableColumnEntity tableColumnEntity = targetReflexForeignkeyColumnMap.get(key);
            field=tableColumnEntity.getField();
            if(field.getType()==thisObject.getClass()){
                query=    returnClassDao.getQueryEntity();
                //field=tableColumnEntity.getField();

            }else {
                continue;
            }
        }

        if(field==null||query==null){
            throw     new Exception("请在"+targetClass.getName()+" 中 设置外键关联 "+thisObject.getClass().getName());
        }

        DebugLog.e(query.getClass().getName() +"  "+thisObject.getClass().getName()+"  "+field.getName());
        query=  new EntityParse<Object>(query).setEntityValue(query,thisObject,field);*/



}
