package com.miniorm.dao.database;


import com.miniorm.dao.utils.ReflexEntity;

import java.util.List;

public interface DeleteInterface {

	public <T> String delete(T t, ReflexEntity reflexEntity) throws IllegalAccessException;
	
	public <T> String delete(List<T> t, ReflexEntity reflexEntity);
	public <T> String deleteAll(T t, ReflexEntity reflexEntity);


}
