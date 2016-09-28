package com.miniorm.dao.database;


import com.miniorm.dao.utils.ReflexEntity;

public interface DatabaseExeInterface<N> {
		
 	public <N> N excuteQuery(String sql, ReflexEntity t);
	
 	public  int excuteUpdate(String sql);

}
