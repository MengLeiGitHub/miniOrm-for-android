package com.miniorm.query;

import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.query.sql.QueryLastInsertRowIdCreater;
import com.miniorm.query.sql.SQLCreater;

public class QueryLastInsertRowId<T>  extends  BaseQuery<T,Object>{

	public QueryLastInsertRowId(ReflexEntity reflexEntity, Class<T> t) {
		super(reflexEntity, t);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SQLCreater<T> getSQLCreater() {
		// TODO Auto-generated method stub
		return new QueryLastInsertRowIdCreater<T>(reflexEntity, t);
	}

	
 
}
