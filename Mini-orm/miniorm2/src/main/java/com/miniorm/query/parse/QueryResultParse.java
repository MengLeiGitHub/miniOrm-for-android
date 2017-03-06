package com.miniorm.query.parse;

import android.database.Cursor;

import com.miniorm.android.parseType.ParseTypeFactory;
import com.miniorm.android.parseType.ParseTypeInterface;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.constant.ParamConstant;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.debug.DebugLog;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.entity.TableIdEntity;
import com.miniorm.enumtype.Parmary;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class QueryResultParse extends BaseResultParse<Cursor> {

    @SuppressWarnings("unchecked")

    @Override
    public <T> T parse(Cursor cursor, T t, ReflexEntity reflexEntity) throws Exception {
        // TODO Auto-generated method stub
            DebugLog.e("cursor.length="+cursor.getCount());

        if (cursor != null && cursor.moveToFirst()) {
            T t1 = (T) t.getClass().newInstance();
            t1 = resolveCursor(reflexEntity, t1, cursor, true);
            if (cursor != null)
                cursor.close();
            return t1;
        } else {
            if (cursor != null)
                cursor.close();
            return t;
        }

    }


    private <T> T resolveCursor(ReflexEntity reflexEntity, T t1, Cursor cursor, boolean isFlag) throws Exception {

        HashMap<String, TableColumnEntity> tableColumnMap = reflexEntity.getTableColumnMap();

        Set<Entry<String, TableColumnEntity>> entrySet = tableColumnMap.entrySet();

        Iterator<Map.Entry<String, TableColumnEntity>> iter = entrySet.iterator();
        String tableName = reflexEntity.getTableName();
        EntityParse<T> entityParse = new EntityParse<T>(t1);

        while (iter.hasNext()) {
            Map.Entry<String, TableColumnEntity> map = iter.next();
            TableColumnEntity tableColumnEntity = map.getValue();
            String columnName = tableColumnEntity.getColumnName();
            Field field = tableColumnEntity.getField();
            String columnAlis=columnName;
            if(Alias)  columnAlis = tableName + "_" + columnName;

            int index = cursor.getColumnIndex(columnAlis);

            if(index<0)continue;

            if (field.getType() == String.class) {
                t1 = initFieldVal(entityParse, String.class, t1, cursor, index, field);
            } else if (field.getType() == int.class) {
                t1 = initFieldVal(entityParse, int.class, t1, cursor, index, field);
            } else if (field.getType() == boolean.class) {
                t1 = initFieldBooleanVal(entityParse, t1, cursor, index, field);
            } else if(field.getType()==long.class){
                t1 = initFieldVal(entityParse, long.class, t1, cursor, index, field);
            } else if (field.getType() == Integer.class) {
                t1 = initFieldVal(entityParse, Integer.class, t1, cursor, index, field);
            }else if(field.getType()==Long.class){
                t1 = initFieldVal(entityParse, Long.class, t1, cursor, index, field);
            }else if (field.getType() == Boolean.class) {
                t1 = initFieldBooleanVal(entityParse, t1, cursor, index, field);
            }
            else {//外键
                if (!tableColumnEntity.isForeignkey()) {//不是
                    t1 = initFieldVal(entityParse, field.getType(), t1, cursor, index, field);
                } else {//是
                    ReflexEntity reflexEntity2 = ReflexCache.getReflexEntity(field.getType().getName());
                    Object o2 = field.getType().newInstance();
                    if (isFlag && tableColumnEntity.isHierarchicalQueries()) {//只有当是外键并且查询关联及第二层解析的时候执行
                       // DebugLog.e(o2.getClass().getName());
                        o2 = resolveCursor(reflexEntity2, o2, cursor, false);
                        t1 = entityParse.setEntityValue(t1, o2, field);
                    } else {//先给ID赋值   在将外键对象赋值到对象中
                        TableIdEntity tableIdEntity = reflexEntity2.getTableIdEntity();
                        Field fieldId=tableIdEntity.getField();
                        Object idval=null ;
                        if(fieldId.getType()==int.class || fieldId.getType()==Integer.class ){
                            idval = new Integer(0);
                         }else if(fieldId.getType()==long.class ||fieldId.getType()==Long.class ){
                            idval=new Long(0);
                        }
                        if(idval==null)
                        idval = fieldId.getType().newInstance();//外键对象的主键
                        DebugLog.e(idval.getClass().getName()+" "+field.getName());
                        o2=  ChoseIDFieldType(new EntityParse<Object>(o2), o2, cursor, index, fieldId);

                      //  idval = initIDVal(reflexEntity2, cursor, idval);

                       // o2 = new EntityParse<Object>(o2).setEntityValue(o2, idval,fieldId);

                        t1 = entityParse.setEntityValue(t1, o2, field);

                    }

                }
            }


        }

        t1 = initIDVal(reflexEntity, cursor, t1);


        return t1;
    }

    private <T> T  ChoseIDFieldType(EntityParse<T> entityParse,  T t1, Cursor cursor, int index, Field field) throws Exception{
        if (field.getType() == int.class) {
            t1 = initFieldVal(entityParse, int.class, t1, cursor, index, field);
        }else if (field.getType() == long.class) {
            t1 = initFieldVal(entityParse, long.class, t1, cursor, index, field);
        }else if (field.getType() == String.class) {
            t1 = initFieldVal(entityParse, String.class, t1, cursor, index, field);
        } else if (field.getType() == Integer.class) {
            t1 = initFieldVal(entityParse, Integer.class, t1, cursor, index, field);
        }
        return t1;
    }

    private <T> T initIDVal(ReflexEntity reflexEntity, Cursor cursor, T t1)throws Exception {
        EntityParse<T> entityParse = new EntityParse<T>(t1);
        String tableName = reflexEntity.getTableName();
        TableIdEntity tableIdEntity = reflexEntity.getTableIdEntity();
        String columnName = tableIdEntity.getColumnName();
        Field field = tableIdEntity.getField();
        String columnAlis =columnName;
        if(Alias)columnAlis= tableName + "_" + columnName;
        int index = cursor.getColumnIndex(columnAlis);
        t1= ChoseIDFieldType(entityParse,  t1, cursor, index, field);
        return t1;

    }


    private <T> T initFieldVal(EntityParse<T> entityParse, Class<?> cls, T t1, Cursor cursor, int index, Field field) throws Exception{

        if (index < 0) return t1;
        ParseTypeInterface parseTypeInterface = ParseTypeFactory
                .getFieldParser(cls);
        if (parseTypeInterface == null) return t1;
        Object obj = parseTypeInterface.getValFromCursor(cursor, index);

        t1 = entityParse.setEntityValue(t1, obj, field);
        return t1;

    }
    private <T> T initFieldBooleanVal(EntityParse<T> entityParse, T t1, Cursor cursor, int index, Field field) throws Exception{

        if (index < 0) return t1;
        ParseTypeInterface parseTypeInterface = ParseTypeFactory
                .getFieldParser(int.class);
        if (parseTypeInterface == null) return t1;

        int obj =(int) parseTypeInterface.getValFromCursor(cursor, index);
       boolean  val=obj== ParamConstant.BOOLEAN_TRUE?true:false;
        t1 = entityParse.setEntityValue(t1, val, field);
        return t1;

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> parseList(Cursor cursor, T t, ReflexEntity reflexEntity) throws Exception {
        // TODO Auto-generated method stub
        ArrayList<T> list = new ArrayList<T>();
        if (cursor != null)
            DebugLog.e("cursor.getCount()=" + cursor.getCount());

        if (cursor != null && cursor.moveToFirst()) {
            do {

                T t1 = (T) t.getClass().newInstance();
                t1 = resolveCursor(reflexEntity, t1, cursor, true);
                list.add(t1);


            } while (cursor.moveToNext());
        }

        if (cursor != null)
            cursor.close();

        return list;
    }

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
