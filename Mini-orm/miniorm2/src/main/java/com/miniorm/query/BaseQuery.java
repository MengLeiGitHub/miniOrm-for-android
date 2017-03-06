package com.miniorm.query;


import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.query.parse.BaseResultParse;
import com.miniorm.query.parse.QueryResultParse;
import com.miniorm.query.sql.SQLCreater;



/**
 * 查询基类
 * @author ML
 * 2017年2月22日13:11:22
 *
 */
public abstract class BaseQuery<T,N> {
	protected ReflexEntity reflexEntity;
	protected T  t;
	public BaseQuery(	ReflexEntity reflexEntity,T t){
			this.reflexEntity=reflexEntity;
			this.t=t;
	}
	
	public  abstract  SQLCreater<T>   getSQLCreater();

	@SuppressWarnings("unchecked")
	public    BaseResultParse<N>   getResultParse(){

		return   (BaseResultParse) new QueryResultParse();
	}
	
}
