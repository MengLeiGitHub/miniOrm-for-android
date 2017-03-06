package com.miniorm.query.analysis.general;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.debug.DebugLog;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.entity.TableIdEntity;
import com.miniorm.query.analysis.BaseSqlAnalysis;
import com.miniorm.query.analysis.SQL;

/**
 * Created by ML on 2017-02-15.
 */

public abstract class GeneralSqlAnalysis<T> extends BaseSqlAnalysis<T> {

  

    public GeneralSqlAnalysis(ReflexEntity reflexEntity, T t) {
		super(reflexEntity, t);
		// TODO Auto-generated constructor stub
	}

	@Override
    public SQL selectQueryField() {
    	ReflexEntity reflexEntity=getReflexEntity();
    	String  sql=getFromTables(reflexEntity);
		DebugLog.e("-------------"+sql);
        return new SQL(sql);
    }

    private String getFromTables(ReflexEntity reflexEntity){
		StringBuilder sb=new StringBuilder();
		String tableName=reflexEntity.getTableName();
		HashMap<String, TableColumnEntity> tableColumnMap=reflexEntity.getTableColumnMap();
		Set<Entry<String, TableColumnEntity>> entrySet = tableColumnMap.entrySet();
		Iterator<Entry<String, TableColumnEntity>> iter = entrySet.iterator();
		while (iter.hasNext())
		{
			Entry<String, TableColumnEntity> map=iter.next();
			TableColumnEntity tableColumnEntity=map.getValue();
			String columnName=tableColumnEntity.getColumnName();
			sb.append(tableName);
			sb.append(".");
			sb.append(columnName);
			sb.append("  as  ");
			sb.append(tableName);
			sb.append("_");
			sb.append(columnName);
			sb.append(",");
			sb.append("\n");
		}
		TableIdEntity  tableIdEntity=reflexEntity.getTableIdEntity();
		sb.append(tableName);
		sb.append(".");
		sb.append(tableIdEntity.getColumnName());
		
		sb.append("   as   ");
		sb.append(tableName);
		sb.append("_");
		sb.append(tableIdEntity.getColumnName());
		sb.append("   \n   ");
		return sb.toString();
	}
    
    
    
    @Override
    public SQL fromTables() {
        return new SQL(getReflexEntity().getTableName());
    }



}
