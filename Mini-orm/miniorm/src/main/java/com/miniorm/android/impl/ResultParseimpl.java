package com.miniorm.android.impl;

import android.database.Cursor;
import android.util.Log;

import com.miniorm.android.parseType.ParseTypeInterface;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.dao.BaseDao;
import com.miniorm.dao.database.BaseResultParseInterface;
import com.miniorm.dao.utils.EntityParse;
import com.miniorm.dao.utils.ReflexEntity;
import com.miniorm.android.parseType.ParseTypeFactory;
import com.miniorm.entity.TableIdEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultParseimpl implements BaseResultParseInterface<Cursor> {

    public <T> T parse(Cursor cursor, T t, ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException {
        // TODO Auto-generated method stub

        EntityParse entityParse = new EntityParse<T>(t);
        HashMap<String, Field> hashmap = entityParse
                .getColumnAndField(t);

        for (String column : hashmap.keySet()) {
            int index = cursor.getColumnIndex(column);
            Field field = hashmap.get(column);

            TableColumn table = field.getAnnotation(TableColumn.class);
            TableID tableID = field.getAnnotation(TableID.class);
            if (table != null && !table.isForeignkey() || tableID != null) {
                ParseTypeInterface parseTypeInterface = ParseTypeFactory
                        .getFieldParser(field.getType());

                Object obj = parseTypeInterface.getValFromCursor(cursor, index);
                t = (T) entityParse.setEntityValue(t, obj, field);
            } else {
                BaseDao baseDao = ParseTypeFactory.getEntityParse(field.getType().getName());
                field.setAccessible(true);
                TableIdEntity tableId = entityParse.getTableIDEntity(field.get(t));

                ParseTypeInterface parseTypeInterface = ParseTypeFactory.getFieldParser(tableId.getField().getType());

                Object obj = parseTypeInterface.getValFromCursor(cursor, index);

                Object obj1 = baseDao.queryById(obj.toString());

                t = (T) entityParse.setEntityValue(t, obj1, field);
            }
        }


        if (cursor != null)
            cursor.close();
        return t;
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

                        Object obj = parseTypeInterface.getValFromCursor(cursor, index);
                        t1 = (T) entityParse.setEntityValue(t1, obj, field);
                    } else {
                        BaseDao baseDao = ParseTypeFactory.getEntityParse(field.getType().getName());
                        field.setAccessible(true);
                        TableIdEntity tableId = entityParse.getTableIDEntity(field.get(t));

                        Log.e("tag", field.getType().getName() + "   " + (tableId == null));

                        ParseTypeInterface parseTypeInterface = ParseTypeFactory.getFieldParser(tableId.getField().getType());

                        Object obj = parseTypeInterface.getValFromCursor(cursor, index);

                        Object obj1 = baseDao.queryById(obj.toString());

                        t1 = (T) entityParse.setEntityValue(t1, obj1, field);
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
