package com.miniorm.android.impl;


import com.miniorm.android.ColumnType;
import com.miniorm.android.TableUtils;
import com.miniorm.dao.database.TableInterface;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.dao.utils.StringUtils;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.entity.TableIdEntity;
import com.miniorm.enumtype.Parmary;

import java.util.HashMap;

public class TableImpl implements TableInterface {

	public String drop(ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub

		return "DROP TABLE IF EXISTS   " + reflexEntity.getTableName() + ";";
  	}

	public String create(Object obj,ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException {
		// TODO Auto-generated method stub

 		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE if not exists ");
		sb.append(" "+reflexEntity.getTableEntity().getTableName());
		sb.append(" (");
		TableIdEntity tableID = reflexEntity.getTableIdEntity();

		if (tableID!=null&&tableID.isPrimaryKey()) {
			
			sb.append(" "+tableID.getName());
			
			sb.append(tableID.getColumnType());
			
			sb.append(ColumnType.PRIMARYKEY);
			
			if (tableID.getKeytype() == Parmary.AutoIncrement) {
				
				sb.append(ColumnType.AUTOINCREMENT);

			}else if(tableID.getKeytype()==Parmary.CUSTOM){

			}


			sb.append(",");
		}
		StringBuilder  foreig=new StringBuilder();
		HashMap<String, TableColumnEntity>  kv =reflexEntity.getTableColumnMap();
		for(TableColumnEntity entity:  kv.values()){
			if(StringUtils.isNull(entity.getColumnName()))continue;
			sb.append("  "+entity.getColumnName());
			
			sb.append(entity.getColumnType());
			
			sb.append(" ,");
			
			/*if(entity.isForeignkey()){
				Object object=entity.getField().getType().newInstance();

				TableUtils.tableInit(object);

				foreig.append("  FOREIGN KEY(  ");

				foreig.append("  "+entity.getColumnName() );

				ReflexEntity reflexEntity1= ReflexCache.getReflexEntity(object.getClass().getName());



				foreig.append("  ) REFERENCES  ");
				foreig.append(reflexEntity1.getTableEntity().getTableName());
				foreig.append("(");
				foreig.append(reflexEntity1.getTableIdEntity().getColumnName());
				foreig.append(")");
				foreig.append(",");
			}*/
  		}
		if(foreig.length()!=0){
			foreig.deleteCharAt(foreig.length()-1);
			sb.append(foreig);
		}
		else
			sb.deleteCharAt(sb.length()-1);
		sb.append(");");
		
  		return sb.toString();
	}

}
