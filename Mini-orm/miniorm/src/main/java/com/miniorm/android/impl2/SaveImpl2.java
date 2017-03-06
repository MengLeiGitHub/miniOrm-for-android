package com.miniorm.android.impl2;

import com.miniorm.annotation.Table;
import com.miniorm.dao.datebase2.SaveInterface;
import com.miniorm.dao.reflex.MySqliteStatement;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.entity.TableIdEntity;
import com.miniorm.enumtype.Parmary;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2017-02-20.
 */

public class SaveImpl2 implements SaveInterface {
    @Override
    public <T> MySqliteStatement save(T t, ReflexEntity reflexEntity) throws IllegalAccessException {
        MySqliteStatement mySqliteStatement=new MySqliteStatement();
        HashMap<String,Integer> keys=new HashMap<>();
        HashMap<Integer,Object> vals=new HashMap<>();



        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");

        sb.append(reflexEntity.getTableName());
        sb.append(" (");

        StringBuilder values = new StringBuilder();


        HashMap<String, TableColumnEntity> tableColumnMap=reflexEntity.getTableColumnMap();

        Set<HashMap.Entry<String, TableColumnEntity>> entrySet = tableColumnMap.entrySet();

        Iterator<Map.Entry<String, TableColumnEntity>> iter = entrySet.iterator();
        boolean isDeleteDouHao=false;
        int i=0;
        while (iter.hasNext())
        {
            Map.Entry<String, TableColumnEntity> map=iter.next();
            TableColumnEntity tableColumnEntity=map.getValue();
            String columnName=tableColumnEntity.getColumnName();
            Field field=tableColumnEntity.getField();
            field.setAccessible(true);
            Object obj=field.get(t);
            if(obj == null){
                continue;
            }
            sb.append(columnName + ",");
            keys.put(columnName,i);
            values.append("?,");

            {
                Table table=obj.getClass().getAnnotation(Table.class);
                if(table==null){
                    vals.put(new Integer(i),obj);

                }else{
                    ReflexEntity  fieldObjReflex= ReflexCache.getReflexEntity(obj.getClass().getName());
                    TableIdEntity tableIdEntity=fieldObjReflex.getTableIdEntity();
                    Field field1=tableIdEntity.getField();
                    field1.setAccessible(true);
                    Object objects=field1.get(obj);
                    vals.put(new Integer(i),objects);
                }


            }

            isDeleteDouHao=true;
            i++;
        }


        TableIdEntity tableIdEntity=reflexEntity.getTableIdEntity();
        if(tableIdEntity.getKeytype()== Parmary.CUSTOM){
            sb.append(" ");
            sb.append(tableIdEntity.getColumnName());
            sb.append(" ");

            Field field1=tableIdEntity.getField();
            field1.setAccessible(true);
            Object objects=field1.get(t);
            values.append("?");
            vals.put(i++,objects);
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
        mySqliteStatement.setSql(sb.toString());
        mySqliteStatement.setKeys(keys);
        mySqliteStatement.setVal(vals);

        return mySqliteStatement;
    }

    @Override
    public <T> MySqliteStatement save(List<T> list, ReflexEntity reflexEntity) {
        return null;
    }

    @Override
    public <T> MySqliteStatement saveOrUpdate(T t, ReflexEntity reflexEntity) throws IllegalAccessException {
        TableIdEntity tableIdEntity=reflexEntity.getTableIdEntity();
        MySqliteStatement SaveorUpdate=save(t,reflexEntity);
        String  saveorUpdate=SaveorUpdate.getSql();

        if(tableIdEntity.isPrimaryKey()){
            if(tableIdEntity.getKeytype()==Parmary.AutoIncrement){
                StringBuilder stringBuilder=new StringBuilder(saveorUpdate);
                //insert into student(name,age) select 'zz7zz7zz',25 where not exists(select * from student where name='zz7zz7zz' and age=25)
                //insert into student( _id , name ,age ) VALUES ( 2,'zz7zz7zz',35)
                int firstCulumnIndex=stringBuilder.indexOf("(");
                int lastCulumnIndex=stringBuilder.indexOf(")");
                String culumns=	stringBuilder.subSequence(firstCulumnIndex+1,lastCulumnIndex).toString();
                int firstCulumnValueIndex=stringBuilder.lastIndexOf("(");
                int lastCulumnValueIndex=stringBuilder.lastIndexOf(")");

                String head=stringBuilder.substring(0,lastCulumnIndex+1);

                String culumnsValues=stringBuilder.subSequence(firstCulumnValueIndex,lastCulumnValueIndex).toString();
                StringBuilder stringBuilder1=new StringBuilder(head);
                stringBuilder1.append(" select ");

                stringBuilder1.append(culumns);
                stringBuilder1.append("  where  not exists  ");
                stringBuilder1.append(" ( select * from  ");
                stringBuilder1.append(reflexEntity.getTableName());
                stringBuilder1.append(" where  ");
                String[] culums=culumns.split(",");
                String[] values=culumnsValues.split(",");
                for (int i=0;i<values.length;i++){
                    stringBuilder1.append(" ");
                    stringBuilder1.append(culums[i]);
                    stringBuilder1.append("=");
                    stringBuilder1.append(values[i]);
                    if(i!=values.length-1){
                        stringBuilder1.append(" and  ");
                    }
                }
                stringBuilder1.append(")");
                SaveorUpdate.setSql(stringBuilder1.toString());
                return SaveorUpdate;

            }else if(tableIdEntity.getKeytype()==Parmary.CUSTOM){
                String  sql=saveorUpdate.replace("insert into ","REPLACE  into ");
                SaveorUpdate.setSql(sql);
                return 		SaveorUpdate;
            }
        }

        return null;
    }
}
