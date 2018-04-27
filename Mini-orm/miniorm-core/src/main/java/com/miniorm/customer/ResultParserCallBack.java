package com.miniorm.customer;

import android.database.Cursor;

/**
 * Created by admin on 2018/4/16.
 */

public interface ResultParserCallBack<T> {
    public  void   parse(Cursor cursor, T t);
    public  T   getEntity();
}
