package com.miniorm.android;

import android.content.Context;
import android.database.Cursor;

import com.miniorm.MiniOrm;
import com.miniorm.dao.utils.ReflexEntity;
import com.miniorm.dao.database.DatabaseExeInterface;


public class DatabaseExcute  implements DatabaseExeInterface<Cursor> {
   
	private SQLHelper  sqlHelper;
       
	public DatabaseExcute(){

		sqlHelper=new SQLHelper(MiniOrm.application, MiniOrm.version,MiniOrm.dbName);
	}
       
	public Cursor excuteQuery(String sql, ReflexEntity t) {
		// TODO Auto-generated method stub
		String[] selectionArgs=null;
		if(sqlHelper!=null)
		return sqlHelper.rawQuery(sql, selectionArgs);
		else return null;
	}

	public int excuteUpdate(String sql) {
		// TODO Auto-generated method stub

		 return  sqlHelper.execSQL(sql);
	}

	@Override
	public void beginTransaction() {
		sqlHelper.beginTransaction();
	}

	@Override
	public void endTransaction() {
		sqlHelper.endTransaction();
	}

	@Override
	public void setTransactionSuccessful() {
		sqlHelper.setTransactionSuccessful();
	}

}
