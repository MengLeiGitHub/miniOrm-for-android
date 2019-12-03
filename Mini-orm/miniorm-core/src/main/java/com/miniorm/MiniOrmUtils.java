package com.miniorm;

import android.app.Application;

import com.miniorm.android.impl.DatabaseExcute;
import com.miniorm.dao.BaseDao;
import com.miniorm.dao.database.DatabaseExeInterface;
import com.miniorm.dao.utils.ResultType;
import com.miniorm.dao.utils.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by ML on 2018/4/10.
 */

public final class MiniOrmUtils {


    private volatile static HashMap<String, BaseDao> hashMap = new HashMap<>();

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
            throw new RuntimeException( e);
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

    /**
     * 初始化加密数据库时候使用
     * @param context
     * @param dbName
     * @param version
     * @param password
     */
    public void init(Application context, String dbName, int version, String password) {
        MiniOrm.init(context, version, dbName, password);
        try {
            Class cls = Class.forName("com.miniorm.sqlcipher.SqlcipherDatabaseExcute");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("使用加密数据库需要引入 sqlCipherMiniOrm \n " +
                    "  maven{ url 'https://dl.bintray.com/mengleigithub/MyMaven'} \n compile 'com.github.mengleigithub:miniorm-sqlcipher:1.1.0'");
        }

    }

    /**
     * 初始化数据库时候调用
     * @param context
     * @param dbName
     * @param version
     */

    public void init(Application context, String dbName, int version) {
        MiniOrm.init(context, version, dbName);

    }

    /**
     * 未加密数据库设置外置数据库的时候使用
     * @param context
     * @param dbName
     * @param version
     * @param useSDCard
     * @param path
     */
    public void init(Application context, String dbName, int version,boolean useSDCard ,String path) {
        MiniOrm.init(context, version, dbName);
        MiniOrm.useSDCard(useSDCard,path);
    }


    /**
     * 数据库升降级的时候调用
     * @param dao
     */
    public static void addUpdateTable(Class<? extends BaseDao> dao){
        if (dao==null){
            throw new RuntimeException("dao 参数为空！");
        }
        MiniOrm.addUpdateTable(dao);
    }

    public void createTables(){
        if (MiniOrm.version==0|| StringUtils.isNull(MiniOrm.dbName)){
            throw new RuntimeException("调用MiniOrm.init()设置初始化参数");
        }
        create();

    }


    private void create() {
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
