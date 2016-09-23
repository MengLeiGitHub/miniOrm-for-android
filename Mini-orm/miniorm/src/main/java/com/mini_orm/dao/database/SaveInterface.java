package com.mini_orm.dao.database;


import com.mini_orm.dao.utils.ReflexEntity;

import java.util.List;

public interface SaveInterface {
	
	
	public <T>  String  save(T t, ReflexEntity reflexEntity);
	
	public  <T>  String  save(List<T> list, ReflexEntity reflexEntity);
	
	
}
