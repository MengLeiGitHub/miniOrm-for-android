package com.miniorm.query.sql;

import java.util.HashMap;

import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.query.analysis.BaseSqlAnalysis;
import com.miniorm.query.analysis.general.GeneralQueryByIdSqlAnalysis;
import com.miniorm.query.analysis.hierarchical.HierarchicalQueryByIdSqlAnalysis;

public class QueryByIdCreater<T> extends SQLCreater<T>{





	public QueryByIdCreater(ReflexEntity reflexEntity, T t) {
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
		if(hashMap.size()==0){//该表中没有外键
			return   new GeneralQueryByIdSqlAnalysis<T>(reflexEntity,t);
		}else{
			return    new  HierarchicalQueryByIdSqlAnalysis<T>(reflexEntity, t);
		}



	}

}
