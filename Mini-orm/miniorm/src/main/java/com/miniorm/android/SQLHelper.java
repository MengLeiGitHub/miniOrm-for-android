package com.miniorm.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class SQLHelper extends SQLiteOpenHelper {
	
	
 	static String files=Environment.getExternalStorageDirectory().getPath()+File.separator;
	
	private static  String databaseName="test.db";
	
	
	
	SQLiteDatabase db;
	
	public SQLHelper(Context context,int version) {
		super(context, databaseName, null, version);
		db = getReadableDatabase();
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) { 
		 
	}
	
	
	public  int  execSQL(String sql){
		try {
			db.execSQL(sql);
			return 1;
 		}catch (Exception e){
			return 0;
		}
 	}
	
 	
	 
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i("====", "================onUpdate");
		
		 
		 
		 
		onCreate(db);
	}
	public void insert(String table,String nullColumnHack,String ...  str){
		
		ContentValues values = new ContentValues();
		values.put("name", str[0]);
		//db.execSQL(sql);
		db.insert("stu", nullColumnHack , values);
	}

	public Cursor query(String string, Object object, Object object2,
			Object object3, Object object4, Object object5, Object object6) {
		// TODO Auto-generated method stub
		
 		
		
		return db.query("stu", null, null, null, null, null, null);
	}
	
	
	public Cursor rawQuery(String sql,String ...selectionArgs )throws SQLiteException {

		return db.rawQuery(sql, selectionArgs);
	}
	
	public int  update(String table, String whereClause,String[] whereArgs,String... values){
		
		return 	db.update(table, null, whereClause, whereArgs);
 		
		
	}
	
	 
}
