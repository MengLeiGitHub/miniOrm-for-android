package com.mini_orm.android.impl;


import com.mini_orm.android.ColumnType;
import com.mini_orm.dao.database.TableInterface;
import com.mini_orm.dao.utils.ReflexEntity;
import com.mini_orm.entity.TableColumnEntity;
import com.mini_orm.entity.TableIdEntity;
import com.mini_orm.enumtype.Parmary;

import java.util.HashMap;

public class TableImpl implements TableInterface {

	public String drop(ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub
		
		return "DROP TABLE IF EXISTS   " + reflexEntity.getTableName() + ";";
  	}

	public String create(ReflexEntity reflexEntity) {
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

			}
			sb.append(",");
		}
		HashMap<String, TableColumnEntity>  kv =reflexEntity.getTableColumnMap();
		for(TableColumnEntity entity:  kv.values()){
			
			sb.append("  "+entity.getColumnName());
			
			sb.append(entity.getColumnType());
			
			sb.append(" ,");
			
			if(entity.isForeignkey()){
				
				
				sb.append("  FOREIGN KEY(  ");

				sb.append("  "+entity.getColumnName() );
				
				sb.append("  ) REFERENCES XXXX(xxxxid ) ,");
				
			}
  		}
		
		sb.deleteCharAt(sb.length()-1);
		sb.append(");");
		
  		return sb.toString();
	}

}
