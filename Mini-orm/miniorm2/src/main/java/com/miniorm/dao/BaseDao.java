package com.miniorm.dao;

import android.util.Log;

import com.miniorm.android.impl.ResultParseimpl;
import com.miniorm.android.parseType.ParseTypeFactory;
import com.miniorm.dao.builder.QueryBuilder;
import com.miniorm.dao.database.BaseResultParseInterface;
import com.miniorm.dao.database.DatabaseExeInterface;
import com.miniorm.dao.database.DeleteInterface;
import com.miniorm.dao.database.QueryInterface;
import com.miniorm.dao.database.SaveInterface;
import com.miniorm.dao.database.TableInterface;
import com.miniorm.dao.database.UpdateInterface;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.dao.utils.ResultType;
import com.miniorm.debug.DebugLog;

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

    private QueryBuilder<T> queryBuilder;


    public BaseDao() {
        if (t == null) t = getQueryEntity();
        ParseTypeFactory.addEntityParse(t.getClass().getName(), this);
        EntityParse entityParse = new EntityParse(t);
        ReflexCache.addReflexEntity(t.getClass().getName(), entityParse.getFieldValueFromT(t));
    }

    public void setTableInterface(TableInterface tableInterface) {
        this.tableInterface = tableInterface;
    }

    public QueryInterface getQueryInterface() {
        return queryInterface;
    }

    public DeleteInterface getDeleteInterface() {
        return deleteInterface;
    }

    public void setDeleteInterface(DeleteInterface deleteInterface) {
        this.deleteInterface = deleteInterface;
    }

    public int createTable() {
        // TODO Auto-generated method stub
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        String sql = null;
        try {
            sql = tableInterface.create(t, reflexEntity);
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


    public QueryBuilder<T> getQueryBuilder() {
        if (queryBuilder == null) queryBuilder = new QueryBuilder<T>(this);
        return queryBuilder;
    }


    public ReflexEntity getReflexEntity() {
        return ReflexCache.getReflexEntity(t.getClass().getName());
    }


    public void setDatabaseexcute(DatabaseExeInterface databaseexcute) {
        this.databaseexcute = databaseexcute;
    }

    public int save(T t) {
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());

        String saveString = null;
        try {
            saveString = saveInterface.save(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int flag = executeUpadate(saveString);
        if (flag == ResultType.FAIL) return flag;

        flag = queryLastInsertId();

        return flag;
    }

    public int saveNoReturnId(T t) {
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        String saveString = null;
        try {
            saveString = saveInterface.save(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int flag = executeUpadate(saveString);
        return flag;
    }


    public int saveOrUpdate(T t){
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());

        String saveString = null;
        try {
            saveString = saveInterface.saveOrUpdate(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int flag = executeUpadate(saveString);
        if (flag == ResultType.FAIL) return flag;

        flag = queryLastInsertId();

        return flag;
    }




    public int save(List<T> t) {

        int result = 0;
        if (t == null || t.size() == 0) {

            return ResultType.FAIL;
        }
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.get(0).getClass().getName());
        try {
            databaseexcute.beginTransaction();
            for (T tt : t) {
                String saveString = null;

                saveString = saveInterface.save(tt, reflexEntity);

                result = executeUpadateList(saveString);
                if (result == ResultType.FAIL) {
                    break;
                }
            }
            if (result == ResultType.SUCCESS) {
                databaseexcute.setTransactionSuccessful();
            } else if (result == ResultType.FAIL) {

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            databaseexcute.endTransaction();
        }

        return result;
    }

    public int saveOrUpdate(List<T> t) {

        int result = 0;
        if (t == null || t.size() == 0) {

            return ResultType.FAIL;
        }
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.get(0).getClass().getName());
        try {
            databaseexcute.beginTransaction();
            int resultInner;
            for (T tt : t) {
                String saveString = null;
                saveString = saveInterface.saveOrUpdate(tt, reflexEntity);
                resultInner = executeUpadateList(saveString);
                if (resultInner == ResultType.SUCCESS) {
                    result=resultInner;
                }
            }
            if (result == ResultType.SUCCESS) {
                databaseexcute.setTransactionSuccessful();
            } else if (result == ResultType.FAIL) {

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            databaseexcute.endTransaction();
        }

        return result;
    }






    public int update(T t) {

        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        String updatesql = null;
        try {
            updatesql = updateInterface.update(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return executeUpadate(updatesql);

    }

    public int delete(T t) {
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        String deleteSql = null;
        try {
            deleteSql = deleteInterface.delete(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return executeUpadate(deleteSql);
    }

    public int delete(T t, String... condition) {
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());

        //condition  添加的一些条件
        //reflexEntity.setCondition(condition);
        String deleteSql = null;
        try {
            deleteSql = deleteInterface.delete(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        DebugLog.e(deleteSql);
        return executeUpadate(deleteSql);
    }

    public int deleteAll() {

        ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);
        String deleteSql = deleteInterface.deleteAll(t, reflexEntity);
        return executeUpadate(deleteSql);
    }

    public int queryLastInsertId() {
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        String queryString = queryInterface.queryLastInsertRowId(t, reflexEntity);

        return resultParse.ParseLastInsertRowId(
                databaseexcute.excuteQuery(queryString, reflexEntity), t, reflexEntity);
    }

    public List<T> queryAll() {
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());

        String querysql = queryInterface.queryAll(reflexEntity);


        return executeQueryList(querysql);
    }

    public List<T> queryAll(String... condition) {
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        //condition  需要重新设计一个类用来存储 这些 条件

        String querysql = queryInterface.queryAll(reflexEntity);


        return executeQueryList(querysql);
    }

    public List<T> queryList(int start, int end) {
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        //condition  需要重新设计一个类用来存储 这些 条件

        String querysql = queryInterface.queryAll(reflexEntity);


        return executeQueryList(querysql);
    }


    public T queryByEntity(T t) {
        ReflexEntity reflexEntity = getReflexEntity();
        String sql = null;
        try {
            sql = queryInterface.queryByEntity(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        DebugLog.e(sql);
        return executeQuery(sql, t, reflexEntity);
    }
    public List<T> queryListByEntity(T t) {
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());

        String sql = null;
        try {
            sql = queryInterface.queryByEntity(t, reflexEntity);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        DebugLog.e(sql);
        return executeQueryList(sql);
    }

    public T queryById(int id) {

        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        try {
            String querySql = queryInterface.queryById(id, reflexEntity);
            return executeQuery(querySql, t, reflexEntity);

        }catch (Exception e){
            e.printStackTrace();
            DebugLog.e(e.getMessage());
        }
        return null;
    }

    public T queryById(String id) {
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        try {
            String querySql = queryInterface.queryById(id, reflexEntity);

            return executeQuery(querySql, t, reflexEntity);

        }catch (Exception e){
            e.printStackTrace();
            DebugLog.e(e.getMessage());

        }
        return null;
    }

    public abstract T getQueryEntity();

    // public abstract List<T> getEntityList();

    public T executeQuery(String sql, T t, ReflexEntity reflexEntity) {
        DebugLog.e(sql);
        try {

            return (T) resultParse.parse(
                    databaseexcute.excuteQuery(sql, reflexEntity), t, reflexEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public T executeQuery(String sql) {
        DebugLog.e(sql);

        try {
            ReflexEntity reflexEntity=getReflexEntity();
            T t1=getQueryEntity();
            return (T) resultParse.parse(
                    databaseexcute.excuteQuery(sql, reflexEntity), t1, reflexEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<T> executeQueryList(String sql) {

        ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);

        DebugLog.e(sql);
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

    public int executeUpadate(String sql) {
        // TODO Auto-generated method stub


        try {
            databaseexcute.beginTransaction();
            int result = databaseexcute.excuteUpdate(sql);
            if (result == ResultType.SUCCESS) {
                databaseexcute.setTransactionSuccessful();
            } else if (result == ResultType.FAIL) {

            }
            DebugLog.e(sql );
            DebugLog.e("result="+result +"     success="+ResultType.SUCCESS+"  fail="+ResultType.FAIL);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseexcute.endTransaction();
        }


        return ResultType.FAIL;

    }


    private int executeUpadateList(String sql) {
        // TODO Auto-generated method stub

        int result = databaseexcute.excuteUpdate(sql);
        DebugLog.e(sql );
        DebugLog.e("result="+result +"     success="+ResultType.SUCCESS+"  fail="+ResultType.FAIL);
        return result;
    }


    public int drop() {
        return executeUpadate(tableInterface.drop(getReflexEntity()));
    }
}
