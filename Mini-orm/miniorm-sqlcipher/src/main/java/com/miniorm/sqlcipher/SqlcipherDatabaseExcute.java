package com.miniorm.sqlcipher;



import com.miniorm.dao.database.DatabaseExeInterface;
import com.miniorm.dao.reflex.MySqliteStatement;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.debug.DebugLog;
import com.miniorm.entity.KV;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

import java.util.List;


public class SqlcipherDatabaseExcute implements DatabaseExeInterface<Cursor> {

	public  static  long timer;


	private SQLCipherHelper sqlHelper;
	SQLiteDatabase sqLiteDatabase;

	public SqlcipherDatabaseExcute(){
		sqlHelper= SQLCipherHelper.getInstance();
		sqLiteDatabase=sqlHelper.getDb();
	}

	@Override
	public Cursor excuteQuery(String sql, ReflexEntity t) {
		// TODO Auto-generated method stub
		String[] selectionArgs=null;
		if(sqlHelper!=null){
			return sqlHelper.rawQuery(sql, selectionArgs);
		}
		else {
			return null;
		}
	}

	@Override
	public long excuteInsert(MySqliteStatement mySqliteStatement) {


		return sqlHelper.execInsert(holdSQLSteByData(mySqliteStatement));
	}

	@Override
	public int excuteUpdate(String sql) {
		// TODO Auto-generated method stub

		return  sqlHelper.execSQL(sql);
	}

	private SQLiteStatement holdSQLSteByData(MySqliteStatement mySqliteStatement){
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
			}else if(val instanceof Double ){
				sqLiteStatement.bindDouble(index,(double)val);
			}
			else if(val instanceof  Float){
				sqLiteStatement.bindDouble(index,(Double.valueOf( val.toString())));
			}
		}

		timer+=(System.currentTimeMillis()-timer1);
		return  sqLiteStatement;
	}


	@Override
	public int excuteUpdate(MySqliteStatement mySqliteStatement) {

		return  sqlHelper.execSQL2(holdSQLSteByData(mySqliteStatement));
	}

	private void log(List<KV> kvlist) {
		if(kvlist==null){
			return;
		}
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("{");

		for (int i=0;i<kvlist.size();i++) {
			KV kv = kvlist.get(i);
			Object val = kv.getObj();
			if (val != null) {
				stringBuilder.append(kv.getColumnName() + ":" + kv.getObj().toString());
			}
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
