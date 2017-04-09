package com.miniorm.android.impl;

import com.miniorm.constant.ParamConstant;
import com.miniorm.dao.database.SaveInterface;
import com.miniorm.dao.reflex.MySqliteStatement;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.dao.reflex.SqlCache;
import com.miniorm.dao.utils.StringUtils;
import com.miniorm.entity.KV;
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

/**
 * Created by admin on 2017-02-20.
 */

public class SaveImpl implements SaveInterface {

    public static long  timer;
    public static long  Cache;



    @Override
    public <T> MySqliteStatement save(T t, ReflexEntity reflexEntity) throws IllegalAccessException {
        MySqliteStatement mySqliteStatement;
        String sqlname=  reflexEntity.getTableName()+"save(Tt)";
        String sql=  SqlCache.getSqlCache(sqlname);
        if(StringUtils.isNull(sql)){
            sql=cacheSql(reflexEntity);
            SqlCache.addSqlCache(sqlname,sql);
            Cache++;
        }
        mySqliteStatement= initVal(reflexEntity,t);
        mySqliteStatement.setSql(sql);

        return mySqliteStatement;
    }

    private String cacheSql(ReflexEntity reflexEntity) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(reflexEntity.getTableName());
        sb.append(" (");
        StringBuilder values = new StringBuilder();
        HashMap<String, TableColumnEntity> tableColumnMap=reflexEntity.getTableColumnMap();
        Set<HashMap.Entry<String, TableColumnEntity>> entrySet = tableColumnMap.entrySet();
        Iterator<Map.Entry<String, TableColumnEntity>> iter = entrySet.iterator();
        boolean isDeleteDouHao=false;
        while (iter.hasNext())
        {
            Map.Entry<String, TableColumnEntity> map=iter.next();
            TableColumnEntity tableColumnEntity=map.getValue();
            String columnName=tableColumnEntity.getColumnName();
            sb.append(columnName + ",");
            values.append("?,");
            isDeleteDouHao=true;

        }
        TableIdEntity tableIdEntity=reflexEntity.getTableIdEntity();
        if(tableIdEntity.getKeytype()== Parmary.CUSTOM){
            sb.append(" ");
            String columnName=tableIdEntity.getColumnName();
            sb.append(columnName);
            sb.append(" ");
            values.append("?");
            isDeleteDouHao=false;
        }
        if (isDeleteDouHao) {
            sb.deleteCharAt(sb.length() - 1);
            values.deleteCharAt(values.length() - 1);
        }
        sb.append("  ) ");
        sb.append(" values ");
        sb.append(" (");
        sb.append(values.toString());
        sb.append("  ) ;");
        return  sb.toString();


    }


    private <T> MySqliteStatement initVal( ReflexEntity reflexEntity, T t) throws IllegalAccessException {
        long  timer1=System.currentTimeMillis();
        MySqliteStatement mySqliteStatement=new MySqliteStatement();
        HashMap<String, TableColumnEntity> tableColumnMap=reflexEntity.getTableColumnMap();
        Set<HashMap.Entry<String, TableColumnEntity>> entrySet = tableColumnMap.entrySet();
        Iterator<Map.Entry<String, TableColumnEntity>> iter = entrySet.iterator();
        ArrayList<KV> KVlist=new ArrayList<KV>();
        int i=1;
        while (iter.hasNext())
        {
            long  timer4=System.currentTimeMillis();
            KV  kv=new KV();
            kv.setIndex(i);

            Map.Entry<String, TableColumnEntity> map=iter.next();
            TableColumnEntity tableColumnEntity=map.getValue();
            Field field=tableColumnEntity.getField();
            field.setAccessible(true);
            Object obj=field.get(t);
            kv.setColumnName(tableColumnEntity.getColumnName());

            if(obj == null){
                kv.setObj(null);
            }else{
                long timer2= System.currentTimeMillis()-timer4;
                timer+=timer2;

                if(obj instanceof String || obj instanceof Integer ||obj instanceof Long){
                    kv.setObj(obj);
                }else  if(obj instanceof Boolean ){
                   Object object= (((Boolean)obj)? ParamConstant.BOOLEAN_TRUE:ParamConstant.BOOLEAN_FALSE);
                   kv.setObj(object);

                } else {
                    if(!tableColumnEntity.isForeignkey()){//和  getclass().getAnnotion()效果一样
                        kv.setObj(obj);
                    }else{
                        ReflexEntity  fieldObjReflex= ReflexCache.getReflexEntity(obj.getClass().getName());
                        if(fieldObjReflex!=null){
                            TableIdEntity tableIdEntity=fieldObjReflex.getTableIdEntity();
                            Field field1=tableIdEntity.getField();
                            field1.setAccessible(true);
                            Object objects=field1.get(obj);
                            kv.setObj(objects);
                        }
                    }
                }


            }

            i++;
            KVlist.add(kv);
         }

        TableIdEntity tableIdEntity=reflexEntity.getTableIdEntity();
        if(tableIdEntity.getKeytype()== Parmary.CUSTOM){
            Field field1=tableIdEntity.getField();
            field1.setAccessible(true);
            Object objects=field1.get(t);
            KV kv=new KV();
            kv.setIndex(i++);
            kv.setColumnName(tableIdEntity.getColumnName());
            kv.setObj(objects);
            KVlist.add(kv);
        }

        mySqliteStatement.setKvlist(KVlist);

        return  mySqliteStatement;


    }

    @Override
    public <T> MySqliteStatement save(List<T> list, ReflexEntity reflexEntity) {
        return null;
    }

    @Override
    public <T> MySqliteStatement saveOrUpdate(T t, ReflexEntity reflexEntity) throws IllegalAccessException {
        MySqliteStatement SaveorUpdate=save(t,reflexEntity);
        String  saveorUpdate=SaveorUpdate.getSql();
        String  sql=saveorUpdate.replace("insert into ","REPLACE  into ");
        SaveorUpdate.setSql(sql);
        return 		SaveorUpdate;
    }
}
