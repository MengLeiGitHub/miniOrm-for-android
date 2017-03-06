package com.miniorm.android;

import com.miniorm.android.parseType.ParseTypeFactory;
import com.miniorm.dao.BaseDao;
import com.miniorm.dao.BaseDao2;

/**
 * Created by admin on 2016/9/23.
 */
public class TableUtils {

    public  static    void  tableInit(final Object clas){
         BaseDao baseDao= ParseTypeFactory.getEntityParse(clas.getClass().getName());
         baseDao.createTable();

    }


}
