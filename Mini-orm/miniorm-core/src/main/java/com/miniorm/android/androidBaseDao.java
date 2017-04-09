package com.miniorm.android;



import com.miniorm.android.impl.DeleteImpl;

import com.miniorm.android.impl.ResultParseimpl;
import com.miniorm.android.impl.SaveImpl;
import com.miniorm.android.impl.TableImpl;
import com.miniorm.android.impl.Updateimpl;
import com.miniorm.android.impl.DatabaseExcute;
import com.miniorm.dao.BaseDao;

public abstract class androidBaseDao<T>  extends BaseDao<T> {

	public androidBaseDao(){
		setUpdateInterface(new Updateimpl());
		setSaveInterface(new SaveImpl());
		setTableInterface(new TableImpl());
		setDeleteInterface(new DeleteImpl());
		setResultParse(new ResultParseimpl());
		setDatabaseexcute(new DatabaseExcute());

	}
	
}
