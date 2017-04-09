package com.miniorm.dao.database;


import com.miniorm.dao.reflex.ReflexEntity;

public interface TableInterface   {

	public  String  create(Object obj, ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException;
	
	public  String  drop(ReflexEntity reflexEntity);
	
}
