package com.miniorm.query.sql;

import java.util.HashMap;

import com.miniorm.android.KeyWork;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.dao.utils.StringUtils;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.query.analysis.BaseSqlAnalysis;
import com.miniorm.query.analysis.general.GeneralQueryAllSqlAnalysis;
import com.miniorm.query.analysis.general.GeneralQueryByOtherSqlAnalysis;
import com.miniorm.query.analysis.hierarchical.HierarchicalQueryAllAnalysis;
import com.miniorm.query.analysis.hierarchical.HierarchicalQueryByOtherAnalysis;

public class QueryAllCreater<T> extends SQLCreater<T>{


	public QueryAllCreater(ReflexEntity reflexEntity, Class<T> t) {
		super(reflexEntity, t);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <N> String toSQL(N t1) throws Exception{
		StringBuilder sql=new StringBuilder();
		sql.append("select ");
		sql.append(selectQueryField());
		sql.append("   from   ");
		sql.append(getTables());
		/*String fieldCondition=FieldCondition(t1);
		if(StringUtils.isNull(fieldCondition)){
			sql.append(fieldCondition);
		}else {
			sql.append(KeyWork.WHERE);
		}*/
		return sql.toString();

	}


	@Override
	public BaseSqlAnalysis<T> getBaseSqlAnalysis() {
		// TODO Auto-generated method stub
		HashMap<String,TableColumnEntity> hashMap= reflexEntity.getForeignkeyColumnMap();
		if(hashMap.size()==0){//该表中没有外键
			return    new  GeneralQueryAllSqlAnalysis<T>(reflexEntity,t);
		}else{
			return    new  HierarchicalQueryAllAnalysis<T>(reflexEntity, t);
		}
	}

}
