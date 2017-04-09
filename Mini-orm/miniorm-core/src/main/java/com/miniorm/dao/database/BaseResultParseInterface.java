package com.miniorm.dao.database;


import com.miniorm.dao.reflex.ReflexEntity;

import java.util.List;

public interface BaseResultParseInterface<N> {

	public <T> T  parse(N n, Class<T> t, ReflexEntity reflexEntity)throws IllegalAccessException, InstantiationException;
	
	public <T> List<T>  parseList(N n, Class<T> t, ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException;

	public <T> int  ParseLastInsertRowId(N n, Class<T> t, ReflexEntity reflexEntity);

}
