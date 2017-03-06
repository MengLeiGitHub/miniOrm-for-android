package com.miniorm.dao.datebase2;


import com.miniorm.dao.reflex.MySqliteStatement;
import com.miniorm.dao.reflex.ReflexEntity;

import java.util.List;


public interface UpdateInterface {

	public  <T>  MySqliteStatement  update(T t, ReflexEntity reflexEntity) throws IllegalAccessException;
	
	public  <T> MySqliteStatement update(List<T> t, ReflexEntity reflexEntity);

}
