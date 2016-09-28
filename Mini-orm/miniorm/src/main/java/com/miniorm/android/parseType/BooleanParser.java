package com.miniorm.android.parseType;

import android.database.Cursor;

public class BooleanParser implements ParseTypeInterface<Boolean>{

	public Boolean getValFromCursor(Cursor corCursor, int index) {
		// TODO Auto-generated method stub
		return corCursor.getInt(index)==0?false:true;
	}

}
