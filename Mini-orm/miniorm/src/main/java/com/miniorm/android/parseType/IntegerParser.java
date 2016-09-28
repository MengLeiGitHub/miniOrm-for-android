package com.miniorm.android.parseType;

import android.database.Cursor;

public class IntegerParser implements ParseTypeInterface<Integer>{

	public Integer getValFromCursor(Cursor corCursor, int index) {
		// TODO Auto-generated method stub
		return corCursor.getInt(index);
	}

}
