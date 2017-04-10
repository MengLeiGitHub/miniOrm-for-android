package com.miniorm.query.map;

import android.util.Log;

import com.miniorm.MiniOrm;
import com.miniorm.android.androidBaseDao;
import com.miniorm.annotation.Table;
import com.miniorm.dao.builder.Where;
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

public   abstract   class ToOneMapping  {

    public Object proceedFilterToQuery(final Object thisObject, Class<? extends Object> returnclass) throws Exception {

        Table table = returnclass.getAnnotation(Table.class);
        if (table == null) {
            throw new Exception("返回值只能是自定义类型并且用 Table 注解");
        }

        final Class<?> initClass = returnclass;

        ReflexEntity thisreflexEntity = ReflexCache.getReflexEntity(thisObject.getClass().getName());
        HashMap<String, TableColumnEntity> thisReflexForeignkeyColumnMap = thisreflexEntity.getForeignkeyColumnMap();

        Object foreignkeyQuery = null;
        Field foreignkeyfield = null;

        for (String key : thisReflexForeignkeyColumnMap.keySet()) {
            TableColumnEntity tableColumnEntity = thisReflexForeignkeyColumnMap.get(key);
            foreignkeyfield = tableColumnEntity.getField();
            if (foreignkeyfield.getType() == returnclass) {
                foreignkeyfield = tableColumnEntity.getField();
            } else {
                continue;
            }
        }
       /* androidBaseDao baseDao2 = new androidBaseDao<Object>() {

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

        if (foreignkeyfield == null) {

            return   thisObjectNoForeignKey( thisObject,  returnclass,baseDao2);




        } else {
            /*
            //关联关键设置在本类中
             */

            foreignkeyfield.setAccessible(true);
            foreignkeyQuery = new EntityParse<>(returnclass).getObjFromField(foreignkeyfield, thisObject);
            ReflexEntity targetReflex = ReflexCache.getReflexEntity(returnclass.getName());
            TableIdEntity tableIdEntity = targetReflex.getTableIdEntity();
            Field field = tableIdEntity.getField();

            Object idval = new EntityParse<>(null).getObjFromField(field, foreignkeyQuery);


            if (idval == null) {
                throw new Exception("外键Id没有赋值，无法查询");
            } else {
                boolean isIntegerOrLong = (idval instanceof Integer) || (idval instanceof Long);
                if (isIntegerOrLong) {
                    if (idval.toString().equalsIgnoreCase(tableIdEntity.getDefaultVal() + ""))
                        throw new Exception("外键Id没有赋值，无法查询");
                    else {
                        return baseDao2.queryById(idval.toString());
                    }
                }
                return baseDao2.queryByEntity(foreignkeyQuery);
            }

        }

    }

    protected abstract Object thisObjectNoForeignKey(final Object thisObject, Class<? extends Object> returnclass, androidBaseDao baseDao2) throws Exception;


}
