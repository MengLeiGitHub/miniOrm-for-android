package com.miniorm.query.sql;


import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.query.analysis.BaseSqlAnalysis;

public class QueryLastInsertRowIdCreater<T> extends SQLCreater<T>{

	
	
	public QueryLastInsertRowIdCreater(ReflexEntity reflexEntity, T t) {
		super(reflexEntity, t);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public <T>  String toSQL(T t1) {
		// TODO Auto-generated method stub

 		String tableName = reflexEntity.getTableEntity().getTableName();
		StringBuilder sb = new StringBuilder();
		reflexEntity.getTableIdEntity().getColumnName();
		sb.append(" select   ");
		sb.append(" max(");
				sb.append(reflexEntity.getTableIdEntity().getColumnName());
		sb.append(")");
		sb.append("    from  ");
		sb.append(tableName);
		sb.append(";");
		return sb.toString();
		
	}


	@Override
	public BaseSqlAnalysis<T> getBaseSqlAnalysis()
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	 

}
