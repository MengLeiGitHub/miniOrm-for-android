package com.miniorm.sqlcipher;

import com.miniorm.android.impl.DeleteImpl;
import com.miniorm.android.impl.ResultParseimpl;
import com.miniorm.android.impl.SaveImpl;
import com.miniorm.android.impl.TableImpl;
import com.miniorm.android.impl.Updateimpl;
import com.miniorm.annotation.TableDao;
import com.miniorm.customer.ResultParser;
import com.miniorm.customer.ResultParserCallBack;
import com.miniorm.dao.BaseDao;
import com.miniorm.debug.DebugLog;


@TableDao
public abstract class androidSqlcipherDao<T> extends BaseDao<T> {

    public androidSqlcipherDao(){
        setUpdateInterface(new Updateimpl());
        setSaveInterface(new SaveImpl());
        setTableInterface(new TableImpl());
        setDeleteInterface(new DeleteImpl());
        setResultParse(new ResultParseimpl());
        setDatabaseexcute(new SqlcipherDatabaseExcute());
    }


    public static <T> T executeQuery(String sql, ResultParserCallBack<T> resultParserCallBack) {
        DebugLog.e(sql);
        try {
            ResultParser resultParser = new ResultParser(resultParserCallBack);
            return resultParser.parse(new SqlcipherDatabaseExcute().excuteQuery(sql, null), null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
