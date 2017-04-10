package com.miniorm.query.map;

import com.miniorm.android.androidBaseDao;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by admin on 2017-04-09.
 */

public abstract class TableDaoMapping {
    public abstract  Class<? extends androidBaseDao> getDaoByName(String name);

}
