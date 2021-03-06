package com.miniorm.query.map;


import com.miniorm.dao.BaseDao;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017-04-09.
 */

public abstract class TableDaoMapping {
    public abstract Class<? extends BaseDao> getDaoByName(String name);

    public abstract List<Class<? extends BaseDao>> getDaos();

    public abstract Collection<String> allEntryName();

    public abstract Class getProxyClass(String clsName);
}
