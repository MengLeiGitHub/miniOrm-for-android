package com.miniorm.android.parseType.parser;

import android.database.Cursor;

import com.miniorm.android.parseType.ParseTypeInterface;

public class BooleanParser implements ParseTypeInterface<Boolean> {

	public Boolean getValFromCursor(Cursor corCursor, int index) {
		// TODO Auto-generated method stub
		return corCursor.getInt(index)==0?false:true;
	}

}
