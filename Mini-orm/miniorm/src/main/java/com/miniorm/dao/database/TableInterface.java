package com.miniorm.dao.database;


import com.miniorm.dao.utils.ReflexEntity;

public interface TableInterface   {

	public  String  create(ReflexEntity reflexEntity);
	
	public  String  drop(ReflexEntity reflexEntity);
	
}
