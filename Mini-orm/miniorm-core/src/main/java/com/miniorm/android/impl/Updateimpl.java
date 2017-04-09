package com.miniorm.android.impl;



import com.miniorm.android.KeyWork;
import com.miniorm.constant.ParamConstant;
import com.miniorm.dao.database.UpdateInterface;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.MySqliteStatement;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.entity.TableIdEntity;

import java.util.List;

public class Updateimpl implements UpdateInterface {

	public <T> MySqliteStatement update(T t, ReflexEntity rexEntity) throws IllegalAccessException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" update ");
		sb.append(rexEntity.getTableName());
		sb.append("  set ");

		for (TableColumnEntity tableColumn:rexEntity.getTableColumnMap().values()) {
			
			if(tableColumn.isPrimaryKey())continue;
			Object obj = EntityParse.getFieldObjectVal(t,tableColumn.getField());
			if(obj==null)continue;
			if(obj instanceof String){
				if(ParamConstant.ColumnDefaultValue.equals(obj.toString())){

					continue;
				};
			}
			String key=tableColumn.getColumnName();
			sb=appVal(sb,key,obj,tableColumn,",");
		}
		if (rexEntity.getKeyset().size() != 0) {
			sb.deleteCharAt(sb.length()-1);
		}
		
		TableIdEntity id=rexEntity.getTableIdEntity();
		if(id!=null){
			sb.append(KeyWork.WHERE);
			String key=id.getColumnName();
			Object obj = EntityParse.getFieldObjectVal(t,id.getField());
			sb=appVal(sb,key,obj,null,"");
		}
		sb.append(";");

 		return null;
	}


	private  StringBuilder  appVal(StringBuilder sb,String key,Object obj,TableColumnEntity tableColumnEntity,String end){
		if (obj instanceof String) {
			sb.append(key);
			sb.append(" = ");

			sb.append("'");
			sb.append(obj.toString());
			sb.append("'");
			sb.append(end);

		}	else if(obj instanceof Boolean ){
/*
			if(!tableColumnEntity.isIgnoreBooleanParam()){
*/
				sb.append(key);
				sb.append(" = ");
				if(((Boolean) obj).booleanValue())
					sb.append(ParamConstant.BOOLEAN_TRUE  );
				else
					sb.append(ParamConstant.BOOLEAN_FALSE );
				sb.append(end);
/*
			}
*/
		}else  if(obj instanceof Integer ){
			if(((Integer) obj).intValue()==0)
			{

			}
			else{
				sb.append(key);
				sb.append(" = ");
				sb.append(((Integer) obj).intValue());
				sb.append(end);

			}
		}

		else{
			sb.append(key);
			sb.append(" = ");
			sb.append(obj);
			sb.append(end);
		}
		return  sb;
	}

	public  <T> MySqliteStatement update(List<T> t,ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub
		
		
		
		
		return null;
	}

 

	 

}
