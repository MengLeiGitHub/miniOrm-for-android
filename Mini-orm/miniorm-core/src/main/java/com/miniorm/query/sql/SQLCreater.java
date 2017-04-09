package com.miniorm.query.sql;


import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.debug.DebugLog;
import com.miniorm.query.analysis.BaseSqlAnalysis;

public abstract class SQLCreater<T> {
	protected Class<T> t;
	protected ReflexEntity reflexEntity;
	BaseSqlAnalysis<T>  baseSqlAnalysis;
	public SQLCreater(ReflexEntity reflexEntity,Class<T> t){
		this.reflexEntity=reflexEntity;
		this.t=t;
	}




	public <N> String toSQL(N t1) throws Exception{
			StringBuilder sql=new StringBuilder();
			sql.append("select ");
			sql.append(selectQueryField());
			sql.append("   from   ");
			sql.append(getTables());
			sql.append("  where  ");
			sql.append(FieldCondition(t1));
			return sql.toString();	

	}
   
	
	protected String getTables() throws Exception {
		// TODO Auto-generated method stub
	    return	getBaseSqlAnalysis().fromTables().toSQL();
		
	}

	
	protected String selectQueryField() throws Exception {
		// TODO Auto-generated method stub
		return	getBaseSqlAnalysis().selectQueryField().toSQL();
	}

	protected   <N>    String FieldCondition(N t) throws Exception {
		// TODO Auto-generated method stub

		return	getBaseSqlAnalysis().FieldCondition(t).toSQL();
	}


	protected  abstract BaseSqlAnalysis<T> getBaseSqlAnalysis() throws Exception ;
	
	

}
