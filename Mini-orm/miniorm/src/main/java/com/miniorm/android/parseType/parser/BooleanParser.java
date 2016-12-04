package com.miniorm.android.parseType.parser;

import android.database.Cursor;

import com.miniorm.android.parseType.ParseTypeInterface;
import com.miniorm.constant.ParamConstant;

public class BooleanParser implements ParseTypeInterface<Boolean> {

	public Boolean getValFromCursor(Cursor corCursor, int index) {
		// TODO Auto-generated method stub

		return corCursor.getInt(index)== ParamConstant.BOOLEAN_TRUE?false:true;
	}

}
