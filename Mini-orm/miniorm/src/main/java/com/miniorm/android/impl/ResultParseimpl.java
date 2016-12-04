package com.miniorm.android.impl;

import android.database.Cursor;

import com.miniorm.android.parseType.ParseTypeInterface;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.dao.BaseDao;
import com.miniorm.dao.database.BaseResultParseInterface;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.android.parseType.ParseTypeFactory;
import com.miniorm.entity.TableIdEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultParseimpl implements BaseResultParseInterface<Cursor> {

    public <T> T parse(Cursor cursor, T t, ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException {
        // TODO Auto-generated method stub
        if (cursor != null && cursor.moveToFirst()) {
            EntityParse entityParse = new EntityParse<T>(t);
            HashMap<String, Field> hashmap = entityParse
                    .getColumnAndField(t);
            T  t1= (T) t.getClass().newInstance();
            for (String column : hashmap.keySet()) {
                int index = cursor.getColumnIndex(column);
                Field field = hashmap.get(column);

                TableColumn tableColumn = field.getAnnotation(TableColumn.class);
                TableID tableID = field.getAnnotation(TableID.class);
                if (tableColumn != null && !tableColumn.isForeignkey() || tableID != null) {
                    ParseTypeInterface parseTypeInterface = ParseTypeFactory
                            .getFieldParser(field.getType());

                    if(parseTypeInterface!=null) {
                        Object obj = parseTypeInterface.getValFromCursor(cursor, index);
                        t1 = (T) entityParse.setEntityValue(t1, obj, field);
                    }
                } else {
                    BaseDao baseDao = ParseTypeFactory.getEntityParse(field.getType().getName());
                    field.setAccessible(true);
                    Object fieldObj = field.getType().newInstance();
                    TableIdEntity tableId = entityParse.getTableIDEntity(fieldObj);

                    ParseTypeInterface parseTypeInterface = ParseTypeFactory.getFieldParser(tableId.getField().getType());

                    Object obj = parseTypeInterface.getValFromCursor(cursor, index);

                    Object obj1 = baseDao.queryById(obj.toString());

                    t1 = (T) entityParse.setEntityValue(t1, obj1, field);
                }
            }
            if (cursor != null)
                cursor.close();
              return  t1;
        }else {
            if (cursor != null)
                cursor.close();
             return t;
        }

    }

    public <T> List<T> parseList(Cursor cursor, T t, ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException {
        // TODO Auto-generated method stub

        ArrayList<T> list = new ArrayList<T>();
        EntityParse entityParse = new EntityParse<T>(t);
        HashMap<String, Field> hashmap = entityParse
                .getColumnAndField(t);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                T t1 = (T) t.getClass().newInstance();
                for (String column : hashmap.keySet()) {
                    int index = cursor.getColumnIndex(column);
                    Field field = hashmap.get(column);

                    TableColumn table = field.getAnnotation(TableColumn.class);
                    TableID tableID = field.getAnnotation(TableID.class);
                    if (table != null && !table.isForeignkey() || tableID != null) {
                        ParseTypeInterface parseTypeInterface = ParseTypeFactory
                                .getFieldParser(field.getType());

                           if(parseTypeInterface!=null){
                               Object obj = parseTypeInterface.getValFromCursor(cursor, index);
                               t1 = (T) entityParse.setEntityValue(t1, obj, field);
                           }
                    } else {
                        //外键部分

                        //外键实例对象
                        Object fieldObj = field.getType().newInstance();

                        //获取外键表的主键
                        TableIdEntity tableId = entityParse.getTableIDEntity(fieldObj);

                        //获取外键主键的 类型  转换
                        ParseTypeInterface parseTypeInterface = ParseTypeFactory.getFieldParser(tableId.getField().getType());

                        if(parseTypeInterface!=null){


                                //获取外键主键值
                                Object obj = parseTypeInterface.getValFromCursor(cursor, index);

                                if(!table.HierarchicalQueries()) //不级联查询外键全部信息
                                {

                                    //获取到外键
                                    ReflexEntity reflexEntity1= ReflexCache.getReflexEntity(fieldObj.getClass().getName());
                                    Field Idfield = reflexEntity1.getTableIdEntity().getField();
                                    fieldObj= entityParse.setEntityValue(fieldObj, obj, Idfield);


                                    t1 = (T) entityParse.setEntityValue(t1, fieldObj, field);
                                }
                                else   //级联查询
                                {
                                    // 查询外键的全部数据
                                    BaseDao baseDao = ParseTypeFactory.getEntityParse(field.getType().getName());
                                    Object obj1 = baseDao.queryById(obj.toString());
                                    t1 = (T) entityParse.setEntityValue(t1, obj1, field);

                                }

                        }

                    }
                }
                list.add(t1);

            } while (cursor.moveToNext());
        }

        if (cursor != null)
            cursor.close();


        return list;
    }

    @Override
    public <T> int ParseLastInsertRowId(Cursor cursor, T t, ReflexEntity reflexEntity) {
        int strid = 0;
        if (cursor.moveToFirst()) {
            strid = cursor.getInt(0);
        }
        if (cursor != null)
            cursor.close();
        return strid;
    }

}
