package com.miniorm.dao.datebase2;


import com.miniorm.dao.reflex.MySqliteStatement;
import com.miniorm.dao.reflex.ReflexEntity;

import java.util.List;

public interface SaveInterface {
	
	
	public  <T>  MySqliteStatement save(T t, ReflexEntity reflexEntity) throws IllegalAccessException;
	
	public  <T>  MySqliteStatement  save(List<T> list, ReflexEntity reflexEntity);

	public  <T>  MySqliteStatement  saveOrUpdate(T t, ReflexEntity reflexEntity) throws IllegalAccessException;


}
