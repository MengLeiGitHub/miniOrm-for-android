package com.miniorm.android;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.miniorm.dao.database.DatabaseExeInterface;
import com.miniorm.dao.reflex.ReflexEntity;


public class DatabaseExcute  implements DatabaseExeInterface<Cursor> {
   
	private SQLHelper  sqlHelper;
       
	public DatabaseExcute(){
		  sqlHelper=SQLHelper.getInstance();
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


	public int excuteUpdate(SQLiteStatement sqLiteStatement){
	 		return  sqlHelper.execSQL2(sqLiteStatement);
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
