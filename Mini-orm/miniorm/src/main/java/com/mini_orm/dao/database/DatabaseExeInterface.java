package com.mini_orm.dao.database;


import com.mini_orm.dao.utils.ReflexEntity;

public interface DatabaseExeInterface<N> {
		
 	public <N> N excuteQuery(String sql, ReflexEntity t);
	
 	public  int excuteUpdate(String sql);

}
