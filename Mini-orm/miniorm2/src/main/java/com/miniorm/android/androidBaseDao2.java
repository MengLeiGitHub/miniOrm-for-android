package com.miniorm.android;



import com.miniorm.android.impl.DeleteImpl;
import com.miniorm.android.impl.QueryImpl;
import com.miniorm.android.impl.ResultParseimpl;
import com.miniorm.android.impl.TableImpl;
import com.miniorm.android.impl2.*;
import com.miniorm.android.impl2.DatabaseExcute;
import com.miniorm.dao.BaseDao2;

public abstract class androidBaseDao2<T>  extends BaseDao2<T> {

	public androidBaseDao2(){
		setQueryInterface(new QueryImpl());
		setUpdateInterface(new Updateimpl());
		setSaveInterface(new SaveImpl());
		setTableInterface(new TableImpl());
		setDeleteInterface(new DeleteImpl());
		setResultParse(new ResultParseimpl());
		setDatabaseexcute(new DatabaseExcute());
	}
	
}
