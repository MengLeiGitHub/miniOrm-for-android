package com.mini_orm.dao.database;


import com.mini_orm.dao.utils.ReflexEntity;

import java.util.List;


public interface UpdateInterface {

	public  <T>  String  update(T t, ReflexEntity reflexEntity);
	
	public  <T>  String  update(List<T> t, ReflexEntity reflexEntity);
		
 
}
