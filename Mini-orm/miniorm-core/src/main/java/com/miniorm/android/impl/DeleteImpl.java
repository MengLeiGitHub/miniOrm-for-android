package com.miniorm.android.impl;


import com.miniorm.android.KeyWork;
import com.miniorm.dao.database.DeleteInterface;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.ProxyCache;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.entity.TableIdEntity;

import java.lang.reflect.Field;
import java.util.List;

public class DeleteImpl implements DeleteInterface {

	@Override
	public <T> String delete(T t, ReflexEntity rexEntity) throws IllegalAccessException {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();
		sb.append(" DELETE FROM  ");
		sb.append(rexEntity.getTableName());
		sb.append("   WHERE   ");

		TableIdEntity tableIdEntity = rexEntity.getTableIdEntity();
		if (tableIdEntity != null) {
			Object obj = EntityParse.getFieldObjectVal(t, tableIdEntity.getField());
			boolean  flag=(obj instanceof Long)||(obj instanceof Integer);
			if(flag&&obj.toString().equalsIgnoreCase("0")){

				StringBuilder stringBuilder=TableIDEqualNullORZERO(t,rexEntity);
				sb.append(stringBuilder);


			}else {
				sb.append(tableIdEntity.getColumnName());
				sb.append("=");
				if (obj instanceof String){
					sb.append("'");
					sb.append(obj);
					sb.append("'");
				}else  if(obj instanceof  Boolean){
					sb.append((((Boolean)obj)?1:0) + ",");
				}
				else{
					sb.append(obj);
				}

			}

		} else {
			StringBuilder stringBuilder=TableIDEqualNullORZERO(t,rexEntity);
			sb.append(stringBuilder);
		}
		

		sb.append(";");

		return sb.toString();

	}


	private StringBuilder TableIDEqualNullORZERO(Object t,ReflexEntity rexEntity)throws IllegalAccessException{
		StringBuilder  sb=new StringBuilder();
		boolean isdeleteAnd = false;
		for (TableColumnEntity tableColumn : rexEntity.getTableColumnMap()
				.values()) {

			if (tableColumn.isPrimaryKey()){
				continue;
			}

			String key=tableColumn.getColumnName();
			Object fildObj = EntityParse.getFieldObjectVal(t,tableColumn.getField());

			if(tableColumn.isForeignkey()){
				String classnmae=tableColumn.getField().getType().getName();

				ReflexEntity reflexEntity=ReflexCache.getReflexEntity(classnmae);
				Field id=reflexEntity.getTableIdEntity().getField();
				Object obj = EntityParse.getFieldObjectVal(fildObj,id);
				sb=FieldConditionUtils.appVal(sb,key,obj,KeyWork.AND);
				isdeleteAnd=true;
				continue;
			}
			sb=FieldConditionUtils.appVal(sb,key,fildObj,KeyWork.AND);

			isdeleteAnd=true;

		}
		if (isdeleteAnd) {
			sb.delete(sb.length() - KeyWork.AND.length(), sb.length() - 1);
		}
		return  sb;
	}









	@Override
	public <T> String delete(List<T> t, ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> String deleteAll(T t, ReflexEntity reflexEntity) {

		StringBuilder sb = new StringBuilder();
		sb.append(" DELETE FROM  ");
		sb.append(reflexEntity.getTableName());

		sb.append(";");

		return sb.toString();
	}





}
