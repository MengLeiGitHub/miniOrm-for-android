package com.miniorm.android.parseType;

import android.database.Cursor;

public class DoubleParser implements ParseTypeInterface<Double>{

	public Double getValFromCursor(Cursor corCursor, int index) {
		// TODO Auto-generated method stub
		return corCursor.getDouble(index);
	}

}
