package com.miniorm;

import android.app.Application;

import com.miniorm.annotation.Table;
import com.miniorm.dao.BaseDao;
import com.miniorm.query.map.TableDaoMapping;

import java.util.ArrayList;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃ ++ + + +
 * ██━██ ┃++
 * <p>
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * <p>
 * ┃　　　┃   Code is far away from bug with the animal protecting
 * ┃　　　┗━━━┓
 * ┃　　　　　　　┣┓
 * ┃　　　　　　　┏┛
 * ┗┓┓┏━┳┓┏┛
 * ┃┫┫　 ┃┫┫
 * ┗┻┛　 ┗┻┛
 * author :ml
 * Created by ML on 2016/9/27.
 */
public class MiniOrm {
    public static Application application;
    public static int version;
    public static String dbName;

    private static ArrayList<Class<BaseDao>> daos;

    public static void init(Application application, int versionCode, String dbName) {
        MiniOrm.application = application;
        MiniOrm.dbName = dbName;
        MiniOrm.version = versionCode;

    }


    private static TableDaoMapping tableDaoMapping;

    public static TableDaoMapping getTableDaoMapping() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(tableDaoMapping==null){
            tableDaoMapping= (TableDaoMapping) Class.forName(TableDaoMapping.class.getName()+"_Child").newInstance();
        }
        return tableDaoMapping;
    }

    /**
     * 在版本升级的时候，调用传入需要升级的表的 dao文件  class类型
     *
     * @param dao
     */

    public static void addUpdateTable(Class dao) {
        if (daos == null) daos = new ArrayList<Class<BaseDao>>();
        daos.add(dao);
    }


    public static ArrayList<Class<BaseDao>> getUpdateTables() {
        return daos;
    }

}
