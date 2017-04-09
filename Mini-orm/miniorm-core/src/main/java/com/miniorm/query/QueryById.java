package com.miniorm.query;

import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.query.parse.BaseResultParse;
import com.miniorm.query.sql.QueryByIdCreater;
import com.miniorm.query.sql.SQLCreater;

public class QueryById<T>  extends  BaseQuery<T,Object>{

	
	public QueryById(ReflexEntity reflexEntity, Class<T> t) {
		super(reflexEntity, t);
		// TODO Auto-generated constructor stub
	}



	

	@Override
	public SQLCreater<T> getSQLCreater() {
		// TODO Auto-generated method stub
		return new QueryByIdCreater<T>(reflexEntity, t);
	}

	 

}
