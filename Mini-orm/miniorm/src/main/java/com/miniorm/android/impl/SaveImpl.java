package com.miniorm.android.impl;



import com.miniorm.annotation.Table;
import com.miniorm.constant.ParamConstant;
import com.miniorm.dao.database.SaveInterface;
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

public class SaveImpl implements SaveInterface {

	public <T> String save(T t, ReflexEntity reflexEntity) throws IllegalAccessException {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		
		sb.append(reflexEntity.getTableName());
		sb.append(" (");
		
		TableIdEntity entity=reflexEntity.getTableIdEntity();
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
			Field field=tableColumnEntity.getField();
			field.setAccessible(true);
			Object obj=field.get(t);
			if(obj == null){
				continue;
			}
			sb.append(columnName + ",");

			if(obj instanceof String)
				values.append("'"+obj+ "',");
			else  if(obj instanceof  Boolean){
				values.append((((Boolean)obj)? ParamConstant.BOOLEAN_TRUE:ParamConstant.BOOLEAN_FALSE) + ",");
 			}
			else{
				Table table=obj.getClass().getAnnotation(Table.class);
				if(table==null){
					values.append( obj+ ",");
				}else{
					ReflexEntity  fieldObjReflex= ReflexCache.getReflexEntity(obj.getClass().getName());
					TableIdEntity tableIdEntity=fieldObjReflex.getTableIdEntity();
					Field field1=tableIdEntity.getField();
					field1.setAccessible(true);
					Object objects=field1.get(obj);
					if(objects instanceof  String ){
						values.append("'"+objects + "',");
					}else
					   values.append(objects + ",");
				}


			}

			isDeleteDouHao=true;
		}

		/*	for (String s : reflexEntity.getKeyset()) {
 			
 			if(s.equals(entity.getColumnName())) continue;
 			
			sb.append(s + ",");
 			Object obj=reflexEntity.getKeyValue().get(s);
			if(obj instanceof String)
			values.append("'"+obj+ "',");
			else
				values.append( obj+ ",");

 		}*/
		TableIdEntity tableIdEntity=reflexEntity.getTableIdEntity();
		if(tableIdEntity.getKeytype()== Parmary.CUSTOM){
			sb.append(" ");
			sb.append(tableIdEntity.getColumnName());
			sb.append(" ");

			Field field1=tableIdEntity.getField();
			field1.setAccessible(true);
			Object objects=field1.get(t);
 			values.append(objects.toString());
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

		return sb.toString();
		
		
	}

	public <T> String save(List<T> list, ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub


		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");

		sb.append(reflexEntity.getTableName());
		sb.append(" (");

		TableIdEntity entity=reflexEntity.getTableIdEntity();
		StringBuilder values = new StringBuilder();
		HashMap<String, TableColumnEntity> tableColumnMap=reflexEntity.getTableColumnMap();
		for (String s : reflexEntity.getKeyset()) {

			if(s.equals(entity.getColumnName())) continue;

			sb.append(s + ",");
			TableColumnEntity tableColumnEntity=tableColumnMap.get(s);

			Object obj=reflexEntity.getKeyValue().get(s);
			if(obj instanceof String)
				values.append("'"+obj+ "',");
			else
				values.append( obj+ ",");

		}
		int size=reflexEntity.getKeyset().size();
		if ( size != 0) {
			sb.deleteCharAt(sb.length() - 1);
			values.deleteCharAt(values.length() - 1);
		}

		sb.append("  ) ");
		sb.append(" values ");
		sb.append(" (");

		sb.append(values.toString());

		sb.append("  ) ;");

		return sb.toString();

	}

	@Override
	public <T> String saveOrUpdate(T t, ReflexEntity reflexEntity) throws IllegalAccessException {
		TableIdEntity tableIdEntity=reflexEntity.getTableIdEntity();
		String  saveorUpdate=save(t,reflexEntity);

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

				return stringBuilder1.toString();

			}else if(tableIdEntity.getKeytype()==Parmary.CUSTOM){

				return 		saveorUpdate.replace("insert into ","REPLACE  into ");
			}
		}

		return "";
	}

}
