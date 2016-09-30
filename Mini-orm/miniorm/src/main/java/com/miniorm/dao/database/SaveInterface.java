package com.miniorm.dao.database;


import com.miniorm.dao.reflex.ReflexEntity;

import java.util.List;

public interface SaveInterface {
	
	
	public <T>  String  save(T t, ReflexEntity reflexEntity) throws IllegalAccessException;
	
	public  <T>  String  save(List<T> list, ReflexEntity reflexEntity);
	
	
}
