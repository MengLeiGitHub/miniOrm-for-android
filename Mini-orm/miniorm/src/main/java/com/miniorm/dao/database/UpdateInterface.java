package com.miniorm.dao.database;


import com.miniorm.dao.reflex.ReflexEntity;

import java.util.List;


public interface UpdateInterface {

	public  <T>  String  update(T t, ReflexEntity reflexEntity) throws IllegalAccessException;
	
	public  <T>  String  update(List<T> t, ReflexEntity reflexEntity);
		
 
}
