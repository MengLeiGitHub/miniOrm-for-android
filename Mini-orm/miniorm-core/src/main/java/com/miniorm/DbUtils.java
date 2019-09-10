package com.miniorm;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;

import com.miniorm.android.SQLHelper;
import com.miniorm.android.impl.DatabaseExcute;
import com.miniorm.customer.ResultParserCallBack;
import com.miniorm.dao.BaseDao;
import com.miniorm.dao.builder.Where;
import com.miniorm.dao.database.DatabaseExeInterface;
import com.miniorm.dao.utils.ResultType;
import com.miniorm.debug.DebugLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Created by ML on 2018/4/10.
 */

public class DbUtils {


   private volatile static WeakHashMap<String, BaseDao> hashMap = new WeakHashMap<>();

    private static final class ChildClass{
        private static DbUtils dbUtils=new DbUtils();
    }

    private DbUtils() {

    }

    public static synchronized DbUtils getInstance() {
         return  ChildClass.dbUtils;
    }

    public synchronized <T> BaseDao<T> getDao(String name){
        try {
           Class cls= Class.forName(name, false,MiniOrm.getApplication().getClassLoader());
           return   getDao(cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized  <T> BaseDao<T> getDao(Class<T> cls) {
        try {
            if (hashMap.get(cls.getName())==null){
                Class<? extends BaseDao>   daocls=   MiniOrm.getTableDaoMapping().getDaoByName(cls.getName());
                hashMap.put(cls.getName(),daocls.newInstance());
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

    public void init(Application context, String dbName, int version, String password){
        MiniOrm.init(context, version, dbName,password);
        create();
    }

    public void init(Application context, String dbName, int version) {
        MiniOrm.init(context, version, dbName);
        create();
    }

    protected void create() {
        Collection<String> strings = null;
        try {
            strings = MiniOrm.getTableDaoMapping().allEntryName();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        for (String key : strings) {
                BaseDao baseDao = getDao(key);
                baseDao.createTable();
        }

    }

    public  int execSQL(String sql,boolean useEncryption) {
        if(useEncryption){
            try {
                Class cls=Class.forName("com.miniorm.sqlcipher.SqlcipherDatabaseExcute");
                DatabaseExeInterface databaseExeInterface= (DatabaseExeInterface) cls.newInstance();
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
        }else {
            int result = new DatabaseExcute().excuteUpdate(sql);
            return result;
        }
    }


}
