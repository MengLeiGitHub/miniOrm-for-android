package com.miniorm.android.impl;



import com.miniorm.annotation.Table;
import com.miniorm.dao.database.SaveInterface;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.entity.TableIdEntity;

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
  			sb.append(columnName + ",");

			if(obj instanceof String)
				values.append("'"+obj+ "',");
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

}
