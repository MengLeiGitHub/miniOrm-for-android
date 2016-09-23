package com.mini_orm.android.impl;

import android.database.Cursor;

import com.mini_orm.android.parseType.ParseTypeFactory;
import com.mini_orm.android.parseType.ParseTypeInterface;
import com.mini_orm.dao.database.BaseResultParseInterface;
import com.mini_orm.dao.utils.EntityParse;
import com.mini_orm.dao.utils.ReflexEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultParseimpl implements BaseResultParseInterface<Cursor> {

	public <T> T parse(Cursor cursor, T t, ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub

		EntityParse entityParse = new EntityParse<T>(t);
		HashMap<String, Field> hashmap = entityParse
				.getColumnAndField(t);

		ParseTypeFactory parseTypeFactory = new ParseTypeFactory();
		boolean flag=cursor.moveToFirst();
		if(flag)
 		for (String column : hashmap.keySet()) {
			int index = cursor.getColumnIndex(column);
			Field field = hashmap.get(column);
			ParseTypeInterface parseTypeInterface = parseTypeFactory
					.getFieldParser(field.getType());
			Object obj = parseTypeInterface.getValFromCursor(cursor, index);
 			t=(T) entityParse.setEntityValue(t, obj, field);
 		}
		return t;
	}

	public <T> List<T> parseList(Cursor cursor, T t, ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException {
		// TODO Auto-generated method stub

		ArrayList<T>   list=new ArrayList<T>();
		EntityParse entityParse = new EntityParse<T>(t);
		HashMap<String, Field> hashmap = entityParse
				.getColumnAndField(t);

		if(cursor!=null&&cursor.moveToFirst()){
 			do{
 				ParseTypeFactory parseTypeFactory = new ParseTypeFactory();
					T t1= (T) t.getClass().newInstance();
					for (String column : hashmap.keySet()) {
						int index = cursor.getColumnIndex(column);
						Field field = hashmap.get(column);
						ParseTypeInterface parseTypeInterface = parseTypeFactory
								.getFieldParser(field.getType());
						Object obj = parseTypeInterface.getValFromCursor(cursor, index);
						t1=(T) entityParse.setEntityValue(t1, obj, field);
					}
				list.add(t1);

 			}while(cursor.moveToNext());
 		}




		return list;
	}

}
