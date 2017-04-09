package com.miniorm.android.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.miniorm.android.SQLHelper;
import com.miniorm.dao.database.DatabaseExeInterface;
import com.miniorm.dao.reflex.MySqliteStatement;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.debug.DebugLog;
import com.miniorm.entity.KV;
import java.util.List;


public class DatabaseExcute implements DatabaseExeInterface<Cursor> {

	public  static  long timer;


	private SQLHelper  sqlHelper;
	SQLiteDatabase sqLiteDatabase;

	public DatabaseExcute(){
		  sqlHelper=SQLHelper.getInstance();
		  sqLiteDatabase=sqlHelper.getDb();
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
	public int excuteUpdate(MySqliteStatement mySqliteStatement) {
		long timer1=System.currentTimeMillis();
	    SQLiteStatement sqLiteStatement=sqLiteDatabase.compileStatement(mySqliteStatement.getSql());
		List<KV> kvlist=mySqliteStatement.getKvlist();
		if(DebugLog.isDebug){
			log(kvlist);
		}
 		for (int i=0;i<kvlist.size();i++){
			KV kv=kvlist.get(i);
			int index = kv.getIndex();
			Object val=kv.getObj();

			if(val==null){
				sqLiteStatement.bindNull(index);
			}else if(val instanceof Integer){
				sqLiteStatement.bindLong(index,Long.valueOf(val.toString()));
			}else if(val instanceof Long){
				sqLiteStatement.bindLong(index,Long.valueOf(val.toString()));
			}else if(val instanceof String){
				sqLiteStatement.bindString(index,val.toString());
			}else if(val instanceof Double )
				sqLiteStatement.bindDouble(index,(double)val);
			else if(val instanceof  Float){
				sqLiteStatement.bindDouble(index,(Double.valueOf( val.toString())));
			}
		}

		timer+=(System.currentTimeMillis()-timer1);
		return  sqlHelper.execSQL2(sqLiteStatement);
	}

	private void log(List<KV> kvlist) {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("{");

		for (int i=0;i<kvlist.size();i++) {
			KV kv = kvlist.get(i);
			Object val = kv.getObj();
			if (val != null)
				stringBuilder.append(kv.getColumnName() + ":" + kv.getObj().toString());
			else {
				stringBuilder.append(kv.getColumnName() + ": null");
			}
		}
		stringBuilder.append("}");
		DebugLog.e(stringBuilder.toString());
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
