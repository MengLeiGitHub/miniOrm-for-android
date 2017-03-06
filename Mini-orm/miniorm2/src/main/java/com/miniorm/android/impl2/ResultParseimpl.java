package com.miniorm.android.impl2;

import android.database.Cursor;

import com.miniorm.android.parseType.ParseTypeFactory;
import com.miniorm.android.parseType.ParseTypeInterface;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.dao.BaseDao;
import com.miniorm.dao.database.BaseResultParseInterface;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.debug.DebugLog;
import com.miniorm.entity.TableIdEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultParseimpl implements BaseResultParseInterface<Cursor> {

    public <T> T parse(Cursor cursor, T t, ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException {
        // TODO Auto-generated method stub
     //   String  tableName=reflexEntity.getTableName();

        if (cursor != null && cursor.moveToFirst()) {
            Object  t1=  t.getClass().newInstance();
            t1= parseObj(reflexEntity,true,t1,cursor);
            if (cursor != null)
                cursor.close();
              return  (T)t1;
        }else {
            if (cursor != null)
                cursor.close();
             return t;
        }

    }

    private  Object   parseObj(ReflexEntity reflexEntity,boolean isDeep,Object object,Cursor  cursor)throws IllegalAccessException, InstantiationException{
        EntityParse entityParse = new EntityParse(object);
        HashMap<String, Field> hashmap = entityParse
                .getColumnAndField(object);
        String tableName=reflexEntity.getTableName();
        Object  t1= object.getClass().newInstance();
        for (String column : hashmap.keySet()) {
            String  columnAlis=tableName+"_"+column;
            int index = cursor.getColumnIndex(columnAlis);
            Field field = hashmap.get(column);
            TableColumn tableColumn = field.getAnnotation(TableColumn.class);
            TableID tableID = field.getAnnotation(TableID.class);
            if (tableColumn != null && !tableColumn.isForeignkey() || tableID != null) {
                ParseTypeInterface parseTypeInterface = ParseTypeFactory
                        .getFieldParser(field.getType());

                if(parseTypeInterface!=null) {
                    Object obj = parseTypeInterface.getValFromCursor(cursor, index);
                    t1 = entityParse.setEntityValue(t1, obj, field);
                }
            } else {





                if(isDeep){
                    ReflexEntity reflexEntity1= ReflexCache.getReflexEntity(field.getType().getName());
                    Object fieldObj = field.getType().newInstance();
                    fieldObj=parseObj(reflexEntity1,false,fieldObj,cursor);
                    t1 = entityParse.setEntityValue(t1, fieldObj, field);
                }

            }
        }





        return  object;
    }



    public <T> List<T> parseList(Cursor cursor, T t, ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException {
        // TODO Auto-generated method stub

        ArrayList<T> list = new ArrayList<T>();
        EntityParse entityParse = new EntityParse<T>(t);
        HashMap<String, Field> hashmap = entityParse
                .getColumnAndField(t);
        if(cursor!=null)
        DebugLog.e("cursor.getCount()="+cursor.getCount());

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
                             //  DebugLog.e("obj="+obj);
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
