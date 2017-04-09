package com.miniorm.query.map;

import com.miniorm.android.androidBaseDao;

/**
 * Created by admin on 2017-04-09.
 */

public class ManyToOneMapping extends ToOneMapping {


/*
    public Object proceedFilterToQuery(final Object thisObject, Class<? extends Object> returnclass) throws Exception {

        Table table = returnclass.getAnnotation(Table.class);
        if (table == null) {
            throw new Exception("返回值只能是自定义类型并且用 Table 注解");
        }

        final Class<?> initClass = returnclass;


        *//**
         * 两种情况 1.是外键管理在本类中 2：外键关联设置在关联对象中
         *//*

        *//**
         * 1，
         *//*
        ReflexEntity thisreflexEntity = ReflexCache.getReflexEntity(thisObject.getClass().getName());
        HashMap<String, TableColumnEntity> thisReflexForeignkeyColumnMap = thisreflexEntity.getForeignkeyColumnMap();

        Object foreignkeyQuery = null;
        Field foreignkeyfield = null;
        *//*

         *//*
        for (String key : thisReflexForeignkeyColumnMap.keySet()) {
            TableColumnEntity tableColumnEntity = thisReflexForeignkeyColumnMap.get(key);
            foreignkeyfield = tableColumnEntity.getField();
            if (foreignkeyfield.getType() == returnclass) {
                foreignkeyfield = tableColumnEntity.getField();
            } else {
                continue;
            }
        }
        androidBaseDao baseDao2 = new androidBaseDao<Object>() {

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

        if (foreignkeyfield == null) {
               //@TableColumn(name = "scid",isForeignkey = true,columnType = ColumnType.VARCHAR)
                throw new Exception("没有发现对应关系，多对一 ，必须在 多的一方的  TableColumn 中 isForeignkey = true ");
        } else {
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

    }*/

    @Override
    protected Object thisObjectNoForeignKey(Object thisObject, Class<? extends Object> returnclass, androidBaseDao baseDao2) throws Exception {
        throw new Exception("没有发现对应关系，多对一 ，必须在 多的一方的  TableColumn 中 isForeignkey = true ");
    }
}
