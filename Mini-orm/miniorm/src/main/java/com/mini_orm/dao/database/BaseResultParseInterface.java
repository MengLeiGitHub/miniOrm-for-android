package com.mini_orm.dao.database;


import com.mini_orm.dao.utils.ReflexEntity;

import java.util.List;

public interface BaseResultParseInterface<N> {

	public <T> T  parse(N n, T t, ReflexEntity reflexEntity);
	
	public <T> List<T>  parseList(N n, T t, ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException;

}
