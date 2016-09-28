package com.miniorm.android.parseType;

import android.database.Cursor;

public class FloatParser implements ParseTypeInterface<Float>{

	public Float getValFromCursor(Cursor corCursor, int index) {
		// TODO Auto-generated method stub
		return corCursor.getFloat(index);
	}

}
