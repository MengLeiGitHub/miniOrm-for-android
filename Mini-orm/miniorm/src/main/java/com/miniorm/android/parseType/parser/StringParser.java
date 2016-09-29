package com.miniorm.android.parseType.parser;

import android.database.Cursor;

import com.miniorm.android.parseType.ParseTypeInterface;

public class StringParser implements ParseTypeInterface<String> {

	public String getValFromCursor(Cursor corCursor, int index) {
		// TODO Auto-generated method stub
		return corCursor.getString(index);
	}

}
