package com.miniorm.sqlcipher;

import com.miniorm.android.androidBaseDao;
import com.miniorm.android.impl.DeleteImpl;
import com.miniorm.android.impl.ResultParseimpl;
import com.miniorm.android.impl.SaveImpl;
import com.miniorm.android.impl.TableImpl;
import com.miniorm.android.impl.Updateimpl;
import com.miniorm.annotation.TableDao;
import com.miniorm.dao.BaseDao;


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

}
