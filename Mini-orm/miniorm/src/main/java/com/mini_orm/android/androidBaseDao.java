package com.mini_orm.android;


import com.mini_orm.android.impl.DeleteImpl;
import com.mini_orm.android.impl.QueryImpl;
import com.mini_orm.android.impl.ResultParseimpl;
import com.mini_orm.android.impl.SaveImpl;
import com.mini_orm.android.impl.TableImpl;
import com.mini_orm.android.impl.Updateimpl;
import com.mini_orm.dao.BaseDao;

public abstract class androidBaseDao<T>  extends BaseDao<T> {
 
	public androidBaseDao(){
		setQueryInterface(new QueryImpl());
		setUpdateInterface(new Updateimpl());
		setSaveInterface(new SaveImpl());
		setTableInterface(new TableImpl());
		setDeleteInterface(new DeleteImpl());
		setResultParse(new ResultParseimpl());
	}
	
}
