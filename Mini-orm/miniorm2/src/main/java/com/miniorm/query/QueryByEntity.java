package com.miniorm.query;

import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.query.parse.BaseResultParse;
import com.miniorm.query.sql.QueryByEntityCreater;
import com.miniorm.query.sql.SQLCreater;

public class QueryByEntity<T>  extends BaseQuery<T,Object>{

	public QueryByEntity(ReflexEntity reflexEntity, T t) {
		super(reflexEntity, t);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SQLCreater<T> getSQLCreater() {
		// TODO Auto-generated method stub
		return new QueryByEntityCreater<T>(reflexEntity, t);
	}



}
