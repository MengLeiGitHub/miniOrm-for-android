package com.mini_orm.android.impl;



import com.mini_orm.dao.database.SaveInterface;
import com.mini_orm.dao.utils.ReflexEntity;
import com.mini_orm.entity.TableIdEntity;

import java.util.List;

public class SaveImpl implements SaveInterface {

	public <T> String save(T t, ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		
		sb.append(reflexEntity.getTableName());
		sb.append(" (");
		
		TableIdEntity entity=reflexEntity.getTableIdEntity();
		
		StringBuilder values = new StringBuilder();
 		for (String s : reflexEntity.getKeyset()) {
 			
 			if(s.equals(entity.getColumnName())) continue;
 			
			sb.append(s + ",");
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

	public <T> String save(List<T> list, ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub
		return null;
	}

}
