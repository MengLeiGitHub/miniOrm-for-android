package com.miniorm.query.map;

import android.util.Log;

import com.miniorm.MiniOrm;
import com.miniorm.android.KeyWork;
import com.miniorm.dao.BaseDao;
import com.miniorm.annotation.ManyToMany;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.ProxyCache;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017-02-28.
 */

public class ManyToManyMapping  {


/*    public List<? extends Object> proceedFilterToQuery(Object thisObject, Method method) throws Exception {
        Class<?> targetClass = null;
        Class<?> returnclass = method.getReturnType();
        if (returnclass != List.class && returnclass != java.util.ArrayList.class && returnclass != java.util.Collection.class) {

            throw new Exception("The framework supports only Collection or subclasses for many to many return types");
        }
        Type genericFieldType = method.getGenericReturnType();
        if (genericFieldType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            if (fieldArgTypes.length != 1) {
                throw new Exception("Do not have generics in the returned type");
            }
            targetClass = (Class<?>) fieldArgTypes[0];
        }

        final Class<?> initClass = targetClass;

        ManyToMany manyToMany = method.getAnnotation(ManyToMany.class);
        final Class<?> bridgingTableClass = manyToMany.bridgingTable();

        androidBaseDao returnClassDao = new androidBaseDao<Object>() {

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
        ReflexEntity bridgingTableReflexEntity = ReflexCache.getReflexEntity(bridgingTableClass.getName());
        if (bridgingTableReflexEntity == null) {
            EntityParse entityParse = new EntityParse(bridgingTableClass);
            Object bridging = bridgingTableClass.newInstance();
            bridgingTableReflexEntity = entityParse.getFieldValueFromT(bridging);
            ReflexCache.addReflexEntity(bridgingTableClass.getName(), bridgingTableReflexEntity);
        }


        String thisObjectBridgingColumnName = null;

        String returnObjectBridgingIdColumnName = null;

        StringBuilder bridgingTableSql = new StringBuilder();
        HashMap<String, TableColumnEntity> bridgingTableReflexEntityTableColumnMap = bridgingTableReflexEntity.getTableColumnMap();
        Field thisObjectbridgingTableIDField = null;
        Field field = null;
        for (String key : bridgingTableReflexEntityTableColumnMap.keySet()) {//一TO多  1
            TableColumnEntity tableColumnEntity = bridgingTableReflexEntityTableColumnMap.get(key);
            field = tableColumnEntity.getField();
            if (field.getType() == thisObject.getClass()) {
                thisObjectBridgingColumnName = tableColumnEntity.getColumnName();
                thisObjectbridgingTableIDField = field;

            } else if (field.getType() == initClass) {
                returnObjectBridgingIdColumnName = tableColumnEntity.getColumnName();

            } else {
                continue;
            }
        }
        Object thisObjectIdVal = null;
        {
            *//*
            //获取当前类中的id的值
             *//*
            Field thisObjectIDField = ReflexCache.getReflexEntity(thisObject.getClass().getName()).getTableIdEntity().getField();
            thisObjectIDField.setAccessible(true);
            thisObjectIdVal = thisObjectIDField.get(thisObject);
        }


        bridgingTableSql.append(KeyWork.SELECT);

        bridgingTableSql.append(returnObjectBridgingIdColumnName);


        bridgingTableSql.append(KeyWork.FROM);


        bridgingTableSql.append(bridgingTableReflexEntity.getTableName());


        bridgingTableSql.append(KeyWork.WHERE);

        bridgingTableSql.append(thisObjectBridgingColumnName);
        bridgingTableSql.append(KeyWork.Eq);
        if (thisObjectbridgingTableIDField.getType() == String.class) {
            bridgingTableSql.append("'");
            bridgingTableSql.append(thisObjectIdVal.toString());
            bridgingTableSql.append("'");
        } else {
            bridgingTableSql.append(thisObjectIdVal.toString());
        }


        ReflexEntity targetReflex = ReflexCache.getReflexEntity(targetClass.getName());

        StringBuilder targetSql = new StringBuilder();

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


        return returnClassDao.executeQueryList(targetSql.toString());


    }*/

    public List<? extends Object> proceedFilterToQuery(Object thisObject, Class returnclass, Class bridgingTable, Class<? extends Collection> collection) throws Exception {


        if (collection != List.class && collection != java.util.ArrayList.class && collection != java.util.Collection.class) {

            throw new Exception("The framework supports only Collection or subclasses for many to many return types");
        }

        final Class<?> initClass = returnclass;

        final Class<?> bridgingTableClass = bridgingTable;


     /*   androidBaseDao returnClassDao = new androidBaseDao<Object>() {
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
        };*/
        BaseDao returnClassDao = MiniOrm.getTableDaoMapping().getDaoByName(initClass.getName()).newInstance();
        ReflexEntity bridgingTableReflexEntity = ReflexCache.getReflexEntity(bridgingTableClass.getName());
        if (bridgingTableReflexEntity == null) {
            EntityParse entityParse = new EntityParse(bridgingTableClass);
            Object bridging = bridgingTableClass.newInstance();
            bridgingTableReflexEntity = entityParse.getFieldValueFromT(bridging);
            ReflexCache.addReflexEntity(bridgingTableClass.getName(), bridgingTableReflexEntity);
        }


        String thisObjectBridgingColumnName = null;

        String returnObjectBridgingIdColumnName = null;

        StringBuilder bridgingTableSql = new StringBuilder();
        HashMap<String, TableColumnEntity> bridgingTableReflexEntityTableColumnMap = bridgingTableReflexEntity.getTableColumnMap();
        Field thisObjectbridgingTableIDField = null;
        Field field = null;
        /*


         */
        for (String key : bridgingTableReflexEntityTableColumnMap.keySet()) {

            TableColumnEntity tableColumnEntity = bridgingTableReflexEntityTableColumnMap.get(key);
            field = tableColumnEntity.getField();
            Class  superClass=thisObject.getClass().getSuperclass();
            if (field.getType() == superClass) {
                thisObjectBridgingColumnName = tableColumnEntity.getColumnName();
                thisObjectbridgingTableIDField = field;

            } else if (field.getType() == initClass) {
                returnObjectBridgingIdColumnName = tableColumnEntity.getColumnName();

            } else {
                continue;
            }
        }

        /*
        //获取当前类中的id的值
         */
        Object thisObjectIdVal = null;
        {
            Field thisObjectIDField = ReflexCache.getReflexEntity(thisObject.getClass().getName()).getTableIdEntity().getField();
            thisObjectIDField.setAccessible(true);
            thisObjectIdVal = thisObjectIDField.get(thisObject);
        }


        bridgingTableSql.append(KeyWork.SELECT);

        bridgingTableSql.append(returnObjectBridgingIdColumnName);


        bridgingTableSql.append(KeyWork.FROM);


        bridgingTableSql.append(bridgingTableReflexEntity.getTableName());


        bridgingTableSql.append(KeyWork.WHERE);

        bridgingTableSql.append(thisObjectBridgingColumnName);
        bridgingTableSql.append(KeyWork.Eq);
        if (thisObjectbridgingTableIDField.getType() == String.class) {
            bridgingTableSql.append("'");
            bridgingTableSql.append(thisObjectIdVal.toString());
            bridgingTableSql.append("'");
        } else {
            bridgingTableSql.append(thisObjectIdVal.toString());
        }


        ReflexEntity targetReflex = ReflexCache.getReflexEntity(returnclass.getName());

        StringBuilder targetSql = new StringBuilder();

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


        return returnClassDao.executeQueryList(targetSql.toString());
    }

}
