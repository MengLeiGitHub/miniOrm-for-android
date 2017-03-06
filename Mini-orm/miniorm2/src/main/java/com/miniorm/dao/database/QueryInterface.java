package com.miniorm.dao.database;


import com.miniorm.dao.reflex.ReflexEntity;

public interface QueryInterface {

	public    String  queryById(int id, ReflexEntity reflexEntity)  throws IllegalAccessException;
	
	public    String  queryById(String id, ReflexEntity reflexEntity)  throws IllegalAccessException;
	
	public  <N>  String  queryByEntity(N t, ReflexEntity reflexEntity) throws IllegalAccessException;

	public    String queryAll(ReflexEntity reflexEntity);

	public  <N>   String    queryLastInsertRowId(N t, ReflexEntity reflexEntity);
	
}
