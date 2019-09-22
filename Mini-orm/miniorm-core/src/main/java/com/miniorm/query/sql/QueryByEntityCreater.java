package com.miniorm.query.sql;

import java.util.HashMap;

import com.miniorm.android.KeyWork;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.query.analysis.BaseSqlAnalysis;
import com.miniorm.query.analysis.general.GeneralQueryByOtherSqlAnalysis;
import com.miniorm.query.analysis.hierarchical.HierarchicalQueryByOtherAnalysis;

public class QueryByEntityCreater<T> extends  SQLCreater<T> {

	public QueryByEntityCreater(ReflexEntity reflexEntity, Class<T> t) {
		super(reflexEntity, t);
		// TODO Auto-generated constructor stub
	}
	@Override
	public <N> String toSQL(N t1) throws Exception {
		StringBuilder sql=new StringBuilder();
		sql.append("select ");
		sql.append(selectQueryField());
		sql.append("   from   ");
		sql.append(getTables()).append(KeyWork.WHERE);
		sql.append(getBaseSqlAnalysis().FieldCondition(t1));
		return sql.toString();
	}


	@Override
	public BaseSqlAnalysis<T> getBaseSqlAnalysis() throws Exception {
		// TODO Auto-generated method stub
		HashMap<String,TableColumnEntity> hashMap= reflexEntity.getForeignkeyColumnMap();
		if(hashMap.size()==0){//该表中没有外键
			return    new  GeneralQueryByOtherSqlAnalysis<T>(reflexEntity,t);
		}else{
			return    new  HierarchicalQueryByOtherAnalysis<T>(reflexEntity, t);
		}

	}

}
