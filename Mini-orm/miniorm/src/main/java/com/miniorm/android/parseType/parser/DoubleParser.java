package com.miniorm.android.parseType.parser;

import android.database.Cursor;

import com.miniorm.android.parseType.ParseTypeInterface;

public class DoubleParser implements ParseTypeInterface<Double> {

	public Double getValFromCursor(Cursor corCursor, int index) {
		// TODO Auto-generated method stub
		return corCursor.getDouble(index);
	}

}
