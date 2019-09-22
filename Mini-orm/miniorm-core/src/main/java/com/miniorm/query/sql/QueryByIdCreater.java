package com.miniorm.query.sql;

import java.util.Collection;
import java.util.HashMap;

import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.query.analysis.BaseSqlAnalysis;
import com.miniorm.query.analysis.general.GeneralQueryByIdSqlAnalysis;
import com.miniorm.query.analysis.hierarchical.HierarchicalQueryByIdSqlAnalysis;

public class QueryByIdCreater<T> extends SQLCreater<T>{





	public QueryByIdCreater(ReflexEntity reflexEntity, Class<T> t) {
		super(reflexEntity, t);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected <T> String FieldCondition(T t) throws Exception {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder();
		String tableid = reflexEntity.getTableIdEntity().getColumnName();
		String tableName=reflexEntity.getTableName();
		sql.append(tableName);
		sql.append(".");
		sql.append(tableid);
		sql.append(" =");
		sql.append(getBaseSqlAnalysis().FieldCondition(t).toSQL());

		return	sql.toString();
	}

	@Override
	public BaseSqlAnalysis<T> getBaseSqlAnalysis() {
		// TODO Auto-generated method stub
		//return   new HierarchicalQueryByIdSqlAnalysis<T>(reflexEntity,t);
		HashMap<String,TableColumnEntity> hashMap= reflexEntity.getForeignkeyColumnMap();
		Collection<TableColumnEntity> list= hashMap.values();
		int i=0;
		for (TableColumnEntity tableColumnEntity:list){
			if (tableColumnEntity.isHierarchicalQueries()){
				i++;
			}
		}
		if(i==0){//该表中没有外键
			return    baseSqlAnalysis=new GeneralQueryByIdSqlAnalysis<T>(reflexEntity,t);
		}else{
			return    baseSqlAnalysis=new HierarchicalQueryByIdSqlAnalysis<T>(reflexEntity, t);
		}



	}

}
