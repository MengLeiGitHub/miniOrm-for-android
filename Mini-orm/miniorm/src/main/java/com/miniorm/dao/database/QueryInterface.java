package com.miniorm.dao.database;


import com.miniorm.dao.utils.ReflexEntity;

public interface QueryInterface {

	public    String  queryById(int id, ReflexEntity reflexEntity);
	
	public    String  queryById(String id, ReflexEntity reflexEntity);
	
	public  <N>  String  queryByEntity(N t, ReflexEntity reflexEntity);

	public    String queryAll(ReflexEntity reflexEntity);

	public  <N>   String    queryLastInsertRowId(N t, ReflexEntity reflexEntity);
	
}
