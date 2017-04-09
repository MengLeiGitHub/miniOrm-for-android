package com.miniorm.android.parseType.parser;

import android.database.Cursor;

import com.miniorm.android.parseType.ParseTypeInterface;

public class IntegerParser implements ParseTypeInterface {

	@SuppressWarnings("unchecked")
	public Integer getValFromCursor(Cursor corCursor, int index) {
		// TODO Auto-generated method stub
		return corCursor.getInt(index);
	}

}
