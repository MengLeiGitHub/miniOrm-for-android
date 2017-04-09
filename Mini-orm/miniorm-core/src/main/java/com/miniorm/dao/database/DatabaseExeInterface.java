package com.miniorm.dao.database;


import com.miniorm.dao.reflex.MySqliteStatement;
import com.miniorm.dao.reflex.ReflexEntity;

public interface DatabaseExeInterface<N> {
		
 	public <N> N  excuteQuery(String sql, ReflexEntity t);
	
 	public  int   excuteUpdate(String sql);
	public  int   excuteUpdate(MySqliteStatement mySqliteStatement);

	public  void  beginTransaction();
	public  void  endTransaction();
	public  void  setTransactionSuccessful();


}
