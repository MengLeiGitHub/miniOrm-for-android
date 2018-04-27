package com.miniorm.customer;

import android.database.Cursor;

import com.miniorm.dao.database.BaseResultParseInterface;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.debug.DebugLog;

import java.util.List;

/**
 * Created by admin on 2018/4/16.
 */

public class ResultParser implements BaseResultParseInterface<Cursor> {
    ResultParserCallBack   resultParserCallBack;
    public ResultParser( ResultParserCallBack   resultParserCallBack){
            this.resultParserCallBack=resultParserCallBack;
    }

    @Override
    public <T> T parse(Cursor cursor, Class<T> t2, ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException {
        if(cursor!=null)
            DebugLog.e("cursor.getCount()="+cursor.getCount());
        T  t= (T) resultParserCallBack.getEntity();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                resultParserCallBack.parse(cursor,t);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return t;
    }

    @Override
    public <T> List<T> parseList(Cursor cursor, Class<T> t, ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    public <T> int ParseLastInsertRowId(Cursor cursor, Class<T> t, ReflexEntity reflexEntity) {
        return 0;
    }
}
