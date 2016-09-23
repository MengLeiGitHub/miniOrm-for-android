package com.mini_orm.android.impl;



import com.mini_orm.android.KeyWork;
import com.mini_orm.dao.database.UpdateInterface;
import com.mini_orm.dao.utils.ReflexEntity;
import com.mini_orm.entity.TableColumnEntity;
import com.mini_orm.entity.TableIdEntity;

import java.util.List;

public class Updateimpl implements UpdateInterface {

 	 
	
	public <T> String update(T t,ReflexEntity rexEntity) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" update ");
		sb.append(rexEntity.getTableName());
		sb.append("  set ");

		for (TableColumnEntity tableColumn:rexEntity.getTableColumnMap().values()) {
			
			if(tableColumn.isPrimaryKey())continue;
			
			
			sb.append(tableColumn.getColumnName());
			sb.append(" = ");

			Object obj = tableColumn.getColumnVal();
			if (obj instanceof String) {
				sb.append("'");
				sb.append(obj.toString());
				sb.append("'");
			}else
				sb.append(obj);

			sb.append(",");
		}
		if (rexEntity.getKeyset().size() != 0) {
			sb.deleteCharAt(sb.length()-1);
		}
		
		TableIdEntity id=rexEntity.getTableIdEntity();
		if(id!=null){
			sb.append(KeyWork.WHERE);
 			sb.append(id.getColumnName());
			sb.append("=");
			sb.append(id.getColumnVal());
		}
		sb.append(";");
 		
		
		return sb.toString();
	}

	public  <T> String update(List<T> t,ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub
		
		
		
		
		return null;
	}

 

	 

}
