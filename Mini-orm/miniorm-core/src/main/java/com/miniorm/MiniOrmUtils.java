package com.miniorm;

import android.app.Application;

import com.miniorm.android.impl.DatabaseExcute;
import com.miniorm.dao.BaseDao;
import com.miniorm.dao.database.DatabaseExeInterface;
import com.miniorm.dao.utils.ResultType;

import java.util.Collection;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by ML on 2018/4/10.
 */

public final class MiniOrmUtils {


    private volatile static WeakHashMap<String, BaseDao> hashMap = new WeakHashMap<>();

    private static final class ChildClass {
        private static MiniOrmUtils dbUtils = new MiniOrmUtils();
    }

    private MiniOrmUtils() {

    }

    public static synchronized MiniOrmUtils getInstance() {
        return ChildClass.dbUtils;
    }

    public synchronized <T> BaseDao<T> getDao(String name) {
        try {
            Class cls = Class.forName(name, false, MiniOrm.getApplication().getClassLoader());
            return getDao(cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private synchronized <T> BaseDao<T> getDaoByDaoClass(Class<? extends BaseDao> cls) {
        try {
            BaseDao baseDao = cls.newInstance();
            String key = baseDao.getQueryEntityClassName();
            hashMap.put(key, baseDao);
            return (BaseDao<T>) baseDao;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("重新编译，缺少文件生成");
        }
    }

    public synchronized <T> BaseDao<T> getDao(Class<T> cls) {
        try {
            if (hashMap.get(cls.getName()) == null) {
                Class<? extends BaseDao> daocls = MiniOrm.getTableDaoMapping().getDaoByName(cls.getName());
                hashMap.put(cls.getName(), daocls.newInstance());
            }
            return (BaseDao<T>) hashMap.get(cls.getName());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void init(Application context, String dbName, int version, String password) {
        MiniOrm.init(context, version, dbName, password);
        create();
    }

    public void init(Application context, String dbName, int version) {
        MiniOrm.init(context, version, dbName);
        create();
    }

    protected void create() {
        try {
            List<Class<? extends BaseDao>> list = MiniOrm.getTableDaoMapping().getDaos();
            for (Class<? extends BaseDao> cls : list){
                BaseDao baseDao = getDaoByDaoClass(cls);
                baseDao.createTable();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public int execSQL(String sql, boolean useEncryption) {
        if (useEncryption) {
            try {
                Class cls = Class.forName("com.miniorm.sqlcipher.SqlcipherDatabaseExcute");
                DatabaseExeInterface databaseExeInterface = (DatabaseExeInterface) cls.newInstance();
                int result = databaseExeInterface.excuteUpdate(sql);
                return result;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            return ResultType.FAIL;
        } else {
            int result = new DatabaseExcute().excuteUpdate(sql);
            return result;
        }
    }


}
