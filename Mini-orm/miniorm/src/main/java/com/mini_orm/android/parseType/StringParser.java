package com.mini_orm.android.parseType;

import android.database.Cursor;

public class StringParser implements ParseTypeInterface<String>{

	public String getValFromCursor(Cursor corCursor, int index) {
		// TODO Auto-generated method stub
		return corCursor.getString(index);
	}

}
