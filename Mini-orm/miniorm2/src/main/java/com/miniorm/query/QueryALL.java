package com.miniorm.query;

import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.query.parse.BaseResultParse;
import com.miniorm.query.sql.QueryAllCreater;
import com.miniorm.query.sql.SQLCreater;

public class QueryALL<T>  extends  BaseQuery<T,Object>{

	
	public QueryALL(ReflexEntity reflexEntity, T t) {
		super(reflexEntity, t);
		// TODO Auto-generated constructor stub
	}






	@Override
	public SQLCreater<T> getSQLCreater() {
		// TODO Auto-generated method stub
		return new QueryAllCreater<T>(reflexEntity, t);
	}

	 

}
