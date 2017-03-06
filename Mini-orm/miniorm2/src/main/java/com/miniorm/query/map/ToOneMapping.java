package com.miniorm.query.map;

import com.miniorm.android.androidBaseDao2;
import com.miniorm.annotation.Table;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.entity.TableIdEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.HashMap;

/**
 * Created by admin on 2017-02-28.
 */

public class ToOneMapping extends BaseMap<Object> {
    @Override
    public Object proceedFilterToQuery(Object thisObject, Method method) throws Exception {
        Class<?> returnclass = method.getReturnType();
        Table table = returnclass.getAnnotation(Table.class);
        if (table == null) {
            throw new Exception("返回值只能是自定义类型并且用 Table 注解");
        }

        final Class<?> initClass = returnclass;

        androidBaseDao2 baseDao2 = new androidBaseDao2<Object>() {

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

        /**
         * 两种情况 1.是外键管理在本类中 2：外键关联设置在关联对象中
         */

        /**
         * 1，
         */
        ReflexEntity thisreflexEntity = ReflexCache.getReflexEntity(thisObject.getClass().getName());
        HashMap<String, TableColumnEntity> thisReflexForeignkeyColumnMap = thisreflexEntity.getForeignkeyColumnMap();

        Object foreignkeyQuery = null;
        Field foreignkeyfield = null;
        for (String key : thisReflexForeignkeyColumnMap.keySet()) {//一TO多  1
            TableColumnEntity tableColumnEntity = thisReflexForeignkeyColumnMap.get(key);
            foreignkeyfield = tableColumnEntity.getField();
            if (foreignkeyfield.getType() == returnclass) {
                foreignkeyfield = tableColumnEntity.getField();
            } else {
                continue;
            }
        }

        if (foreignkeyfield == null) {//关联关键设置在对方的类中
            //throw new Exception("请在" + thisObject.getClass().getName() + " 中 设置外键关联 " + returnclass.getName() + "，否则不能使用ToOneMapping");
            ReflexEntity targetreflexEntity = ReflexCache.getReflexEntity(returnclass.getName());
            HashMap<String, TableColumnEntity> targetReflexForeignkeyColumnMap = targetreflexEntity.getForeignkeyColumnMap();

            for (String key : targetReflexForeignkeyColumnMap.keySet()) {
                TableColumnEntity tableColumnEntity = targetReflexForeignkeyColumnMap.get(key);
                foreignkeyfield = tableColumnEntity.getField();
                if (foreignkeyfield.getType() == thisObject.getClass()) {
                    foreignkeyfield = tableColumnEntity.getField();
                } else {
                    continue;
                }
            }
             if(foreignkeyfield==null){
                 throw new Exception("必须在两个表中的一个，设置外键关联另外一个表");

             }

             Object  targetObj=  returnclass.newInstance();
             targetObj=new EntityParse<>(targetObj).setEntityValue(targetObj,thisObject,foreignkeyfield);
         return    baseDao2.queryByEntity(targetObj);

        } else {//关联关键设置在本类中


            foreignkeyfield.setAccessible(true);
            foreignkeyQuery = new EntityParse<>(thisObject).getObjFromField(foreignkeyfield, thisObject);

            ReflexEntity targetReflex = ReflexCache.getReflexEntity(returnclass.getName());
            TableIdEntity tableIdEntity = targetReflex.getTableIdEntity();
            Field field = tableIdEntity.getField();
            Object idval = new EntityParse<>(foreignkeyQuery).getObjFromField(field, foreignkeyQuery);
            if (idval == null) {
                throw new Exception("外键Id没有赋值，无法查询");
            }
            return baseDao2.queryByEntity(foreignkeyQuery);
        }
    }
}
