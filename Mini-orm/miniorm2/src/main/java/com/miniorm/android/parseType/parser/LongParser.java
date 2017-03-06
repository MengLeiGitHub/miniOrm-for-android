package com.miniorm.android.parseType.parser;

import android.database.Cursor;

import com.miniorm.android.parseType.ParseTypeInterface;

public class LongParser implements ParseTypeInterface {

	@SuppressWarnings("unchecked")
	public Long getValFromCursor(Cursor corCursor, int index) {
		// TODO Auto-generated method stub
		return corCursor.getLong(index);
	}

}
