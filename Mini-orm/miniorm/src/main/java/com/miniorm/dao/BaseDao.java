package com.miniorm.dao;

import android.util.Log;

import com.miniorm.android.parseType.ParseTypeFactory;
import com.miniorm.dao.database.BaseResultParseInterface;
import com.miniorm.dao.database.DeleteInterface;
import com.miniorm.dao.database.QueryInterface;
import com.miniorm.dao.database.TableInterface;
import com.miniorm.dao.database.UpdateInterface;
import com.miniorm.dao.utils.EntityParse;
import com.miniorm.dao.utils.ReflexCache;
import com.miniorm.dao.utils.ReflexEntity;
import com.miniorm.dao.database.DatabaseExeInterface;
import com.miniorm.dao.database.SaveInterface;
import com.miniorm.dao.utils.ResultType;

import java.util.List;

public abstract class BaseDao<T> {

    private SaveInterface saveInterface;

    private UpdateInterface updateInterface;

    private QueryInterface queryInterface;

    private TableInterface tableInterface;

    private BaseResultParseInterface resultParse;

    private DatabaseExeInterface databaseexcute;

    private DeleteInterface deleteInterface;

    String tag = this.getClass().getName();
    T t;

    public BaseDao() {
        if (t == null) t = getQueryEntity();
        ParseTypeFactory.addEntityParse(t.getClass().getName(), this);
        EntityParse entityParse=new EntityParse(t);
        ReflexCache.addReflexEntity(t.getClass().getName(),entityParse.getFieldValueFromT(t));
    }

    public void setTableInterface(TableInterface tableInterface) {
        this.tableInterface = tableInterface;
    }

    public DeleteInterface getDeleteInterface() {
        return deleteInterface;
    }

    public void setDeleteInterface(DeleteInterface deleteInterface) {
        this.deleteInterface = deleteInterface;
    }

    public int createTable() {
        // TODO Auto-generated method stub
        ReflexEntity reflexEntity =ReflexCache.getReflexEntity(t.getClass().getName());
        String sql = null;
        try {
            sql = tableInterface.create(t,reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return ResultType.FAIL;
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return executeUpadate(sql);
    }

    public void setResultParse(BaseResultParseInterface resultParse) {
        this.resultParse = resultParse;
    }

    public <S extends BaseDao<T>> S setSaveInterface(
            SaveInterface saveInterface) {
        this.saveInterface = saveInterface;
        return (S) this;
    }

    public <S extends BaseDao<T>> S setUpdateInterface(
            UpdateInterface updateInterface) {
        this.updateInterface = updateInterface;
        return (S) this;
    }

    public <S extends BaseDao<T>> S setQueryInterface(
            QueryInterface queryInterface) {
        this.queryInterface = queryInterface;
        return (S) this;
    }

    public void setDatabaseexcute(DatabaseExeInterface databaseexcute) {
        this.databaseexcute = databaseexcute;
    }

    public int save(T t) {
        ReflexEntity reflexEntity =ReflexCache.getReflexEntity(t.getClass().getName());
        String saveString = null;
        try {
            saveString = saveInterface.save(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int flag = executeUpadate(saveString);
        if (flag == 0) return flag;

        flag = queryLastInsertId();
        return flag;
    }

    public int saveNoReturnId(T t) {
        ReflexEntity reflexEntity =ReflexCache.getReflexEntity(t.getClass().getName());
        String saveString = null;
        try {
            saveString = saveInterface.save(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int flag = executeUpadate(saveString);
        return flag;
    }

    public int save(List<T> t) {

        databaseexcute.beginTransaction();
        int result = 0;
        if(t==null||t.size()==0){

            return ResultType.FAIL;
        }
        ReflexEntity reflexEntity =ReflexCache.getReflexEntity(t.get(0).getClass().getName());
        for (T tt : t) {
            String saveString = null;
            try {
                saveString = saveInterface.save(tt, reflexEntity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            result = executeUpadateList(saveString);
            if (result == ResultType.FAIL) {
                break;
            }
        }
        if (result == ResultType.SUCCESS) {
            databaseexcute.setTransactionSuccessful();
        } else if (result == ResultType.FAIL) {

        }
        databaseexcute.endTransaction();

        return result;
    }

    public int update(T t) {
        ReflexEntity reflexEntity =ReflexCache.getReflexEntity(t.getClass().getName());
        String updatesql = null;
        try {
            updatesql = updateInterface.update(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return executeUpadate(updatesql);

    }

    public int delete(T t) {
        ReflexEntity reflexEntity =ReflexCache.getReflexEntity(t.getClass().getName());
        String deleteSql = null;
        try {
            deleteSql = deleteInterface.delete(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return executeUpadate(deleteSql);
    }

    public int delete(T t, String... condition) {
        ReflexEntity reflexEntity =ReflexCache.getReflexEntity(t.getClass().getName());

        //condition  添加的一些条件
        //reflexEntity.setCondition(condition);
        String deleteSql = null;
        try {
            deleteSql = deleteInterface.delete(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Log.e(tag, deleteSql);
        return executeUpadate(deleteSql);
    }

    public int deleteAll() {

        ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);
        String deleteSql = deleteInterface.deleteAll(t, reflexEntity);
        return executeUpadate(deleteSql);
    }


    public int queryLastInsertId() {
        ReflexEntity reflexEntity =ReflexCache.getReflexEntity(t.getClass().getName());
        String queryString = queryInterface.queryLastInsertRowId(t, reflexEntity);

        return resultParse.ParseLastInsertRowId(
                databaseexcute.excuteQuery(queryString, reflexEntity), t, reflexEntity);
    }

    public List<T> queryAll() {
        ReflexEntity reflexEntity =ReflexCache.getReflexEntity(t.getClass().getName());

        String querysql = queryInterface.queryAll(reflexEntity);


        return executeQueryList(querysql);
    }

    public List<T> queryAll(String... condition) {
        ReflexEntity reflexEntity =ReflexCache.getReflexEntity(t.getClass().getName());
        //condition  需要重新设计一个类用来存储 这些 条件

        String querysql = queryInterface.queryAll(reflexEntity);


        return executeQueryList(querysql);
    }


    public T queryByEntity(T t) {
        ReflexEntity reflexEntity =ReflexCache.getReflexEntity(t.getClass().getName());

        String sql = null;
        try {
            sql = queryInterface.queryByEntity(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Log.e("tag=====", sql);
        return executeQuery(sql, t, reflexEntity);
    }

    public T queryById(int id) {

        ReflexEntity reflexEntity =ReflexCache.getReflexEntity(t.getClass().getName());

        String querySql = queryInterface.queryById(id, reflexEntity);
        System.out.println(querySql);
        return executeQuery(querySql, t, reflexEntity);
    }

    public T queryById(String id) {
        ReflexEntity reflexEntity =ReflexCache.getReflexEntity(t.getClass().getName());

        String querySql = queryInterface.queryById(id, reflexEntity);

        return executeQuery(querySql, t, reflexEntity);
    }

    public abstract T getQueryEntity();

    // public abstract List<T> getEntityList();

    public T executeQuery(String sql, T t, ReflexEntity reflexEntity) {

        Log.e(tag, sql);

        try {

            return (T) resultParse.parse(
                    databaseexcute.excuteQuery(sql, reflexEntity), t, reflexEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<T> executeQueryList(String sql) {

        ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);

        Log.e(tag, sql);

        try {
            return (List<T>) resultParse.parseList(
                    databaseexcute.excuteQuery(sql, null), t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;

    }

    private int executeUpadate(String sql) {
        // TODO Auto-generated method stub

        Log.e(tag, sql);
        databaseexcute.beginTransaction();
        int result = databaseexcute.excuteUpdate(sql);
        if (result == ResultType.SUCCESS) {
            databaseexcute.setTransactionSuccessful();
        } else if (result == ResultType.FAIL) {

        }
        databaseexcute.endTransaction();

        return result;
    }


    public int executeUpadateList(String sql) {
        // TODO Auto-generated method stub

        Log.e(tag, sql);
        int result = databaseexcute.excuteUpdate(sql);


        return result;
    }


    public int drop() {
        return databaseexcute.excuteUpdate(tableInterface.drop(new EntityParse<T>(t).getFieldValueFromT(t)));
    }
}
