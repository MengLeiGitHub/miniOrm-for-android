package com.miniorm;

import android.app.Application;

import com.miniorm.android.ContextUtils;
import com.miniorm.android.SQLHelper;
import com.miniorm.annotation.Table;
import com.miniorm.constant.MiniOrmDataConfig;
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
    private static Application application;
    public static int version;
    public static String dbName;

    private static ArrayList<Class<? extends BaseDao>> daos;

    public static void init(Application application, int versionCode, String dbName) {
        MiniOrm.application = application;
        MiniOrm.dbName = dbName;
        MiniOrm.version = versionCode;
    }

    /**该方法在创建表之前调用且在初始化init(Application application, int versionCode, String dbName)
     * 方法之后调用，否则将会不能生效
     * @param isUseSdCard  表示是否将数据库文件存在sd卡
     * @param path     sd卡上的路径
     */

    public static void  useSDCard(boolean isUseSdCard,String path){
        MiniOrmDataConfig dataConfig = new MiniOrmDataConfig(MiniOrm.application);
        dataConfig.saveBoolean(MiniOrmDataConfig.Config.USE_SD_CARD+version,isUseSdCard);
        dataConfig.save(MiniOrmDataConfig.Config.PATH_SD_CARD+version,path);
    }



    private static TableDaoMapping tableDaoMapping;

    public static TableDaoMapping getTableDaoMapping() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(tableDaoMapping==null){
            tableDaoMapping= (TableDaoMapping) Class.forName(TableDaoMapping.class.getName()+"_Child",
                    false,MiniOrm.application.getClassLoader()).newInstance();
        }
        return tableDaoMapping;
    }

    /**
     * 在版本升级的时候，调用传入需要升级的表的 dao文件  class类型
     *
     * @param dao
     */

    public static void addUpdateTable(Class<? extends BaseDao> dao) {
        if (daos == null) daos = new ArrayList<>();
        daos.add(dao);
    }


    public static ArrayList<Class<? extends BaseDao>> getUpdateTables() {
        return daos;
    }

    public static Application getApplication() {
        if (application==null) application=ContextUtils.getAppication();
        return application;
    }
}
