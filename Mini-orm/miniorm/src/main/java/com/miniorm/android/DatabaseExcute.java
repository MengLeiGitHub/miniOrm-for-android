package com.miniorm.android;

import android.content.Context;
import android.database.Cursor;

import com.miniorm.dao.utils.ReflexEntity;
import com.miniorm.dao.database.DatabaseExeInterface;


public class DatabaseExcute  implements DatabaseExeInterface<Cursor> {
   
	private SQLHelper  sqlHelper;
       
	public DatabaseExcute(Context context,int version){
		sqlHelper=new SQLHelper(context, version);
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

}
