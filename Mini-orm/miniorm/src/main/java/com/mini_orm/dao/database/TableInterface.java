package com.mini_orm.dao.database;


import com.mini_orm.dao.utils.ReflexEntity;

public interface TableInterface   {

	public  String  create(ReflexEntity reflexEntity);
	
	public  String  drop(ReflexEntity reflexEntity);
	
}
