package com.miniorm.dao;

import com.miniorm.android.parseType.ParseTypeFactory;
import com.miniorm.dao.builder.QueryBuilder;
import com.miniorm.dao.builder.Where;
import com.miniorm.dao.database.BaseResultParseInterface;
import com.miniorm.dao.database.DeleteInterface;
import com.miniorm.dao.database.TableInterface;
import com.miniorm.dao.datebase2.DatabaseExeInterface;
import com.miniorm.dao.datebase2.SaveInterface;
import com.miniorm.dao.datebase2.UpdateInterface;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.MySqliteStatement;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.dao.utils.ResultType;
import com.miniorm.debug.DebugLog;
import com.miniorm.query.BaseQuery;
import com.miniorm.query.QueryALL;
import com.miniorm.query.QueryByEntity;
import com.miniorm.query.QueryById;
import com.miniorm.query.QueryLastInsertRowId;
import com.miniorm.query.parse.BaseResultParse;

import java.util.List;

public abstract class BaseDao2<T> extends BaseDao<T> {

    private SaveInterface saveInterface;

    private UpdateInterface updateInterface;

    //  private QueryInterface queryInterface;


    private BaseQuery<T, Object> baseQuery;

    private TableInterface tableInterface;

    private BaseResultParseInterface resultParse;

    private DatabaseExeInterface databaseexcute;

    private DeleteInterface deleteInterface;

    String tag = this.getClass().getName();
    T t;

    private QueryBuilder<T> queryBuilder;
    ReflexEntity reflexEntity ;


    public BaseDao2() {
        if (t == null) t = getQueryEntity();
        ParseTypeFactory.addEntityParse(t.getClass().getName(), this);
        EntityParse entityParse = new EntityParse(t);
        reflexEntity =  entityParse.getFieldValueFromT(t);
        ReflexCache.addReflexEntity(t.getClass().getName(),reflexEntity);
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

    public <S extends BaseDao2<T>> S setSaveInterface(
            SaveInterface saveInterface) {
        this.saveInterface = saveInterface;
        return (S) this;
    }

    public <S extends BaseDao2<T>> S setUpdateInterface(
            UpdateInterface updateInterface) {
        this.updateInterface = updateInterface;
        return (S) this;
    }

   /* public <S extends BaseDao<T>> S setQueryInterface(
            QueryInterface queryInterface) {
        this.queryInterface = queryInterface;
        return (S) this;
    }*/

    public <S extends BaseDao<T>> S setBaseQuery(BaseQuery<T, Object> baseQuery) {
        this.baseQuery = baseQuery;
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


    public int FastSave(T t) throws IllegalAccessException {
        MySqliteStatement  saveString = saveInterface.save(t, reflexEntity);
        return executeUpadateNoOpenTrancation(saveString);
    }


    public int save(T t) {
     //   ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());

        MySqliteStatement saveString = null;
        try {
            saveString = saveInterface.save(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int flag = executeUpadateNoOpenTrancation(saveString);
        if (flag == ResultType.FAIL) return flag;

        flag = queryLastInsertId();

        return flag;
    }

    public int saveNoReturnId(T t) {
        MySqliteStatement saveString = null;
        try {
            saveString = saveInterface.save(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int flag = executeUpadate(saveString);
        return flag;
    }
    public int FastSaveNoReturnId(T t) throws IllegalAccessException {
        MySqliteStatement  saveString = saveInterface.save(t, reflexEntity);
        return     executeUpadate(saveString);
    }
    public int FastSaveOrUpdate(T t) throws IllegalAccessException {
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        MySqliteStatement   saveString = saveInterface.saveOrUpdate(t, reflexEntity);
        return executeUpadate(saveString);
    }
    public int saveOrUpdate(T t) {
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());

        MySqliteStatement saveString = null;
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
        try {
            databaseexcute.beginTransaction();
            for (T tt : t) {
                MySqliteStatement      saveString = saveInterface.save(tt, reflexEntity);
                result = executeUpadateNoOpenTrancation(saveString);
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
       // ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.get(0).getClass().getName());
        try {
           // semaphoreTransaction.acquire();

            databaseexcute.beginTransaction();
            for (T tt : t) {
                MySqliteStatement saveString = saveInterface.saveOrUpdate(tt, reflexEntity);
                executeUpadateNoOpenTrancation(saveString);
            }
            databaseexcute.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseexcute.endTransaction();
           // semaphoreTransaction.release();

        }

        return result;
    }


    public int update(T t) {

       // ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        MySqliteStatement updatesql = null;
        try {
            updatesql = updateInterface.update(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return executeUpadate(updatesql);

    }

    public int delete(T t) {
     //   ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        String deleteSql = null;
        try {
            deleteSql = deleteInterface.delete(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return executeUpadate(deleteSql);
    }

    public int delete(T t, String... condition) {
       // ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());

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

      //  ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);
        String deleteSql = deleteInterface.deleteAll(t, reflexEntity);
        return executeUpadate(deleteSql);
    }

    public int queryLastInsertId() {
        try {
            ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
            baseQuery = new QueryLastInsertRowId<>(reflexEntity, t);
            String queryString = baseQuery.getSQLCreater().toSQL(t);
            BaseResultParse baseResultParse = baseQuery.getResultParse();
            return baseResultParse.ParseLastInsertRowId(databaseexcute.excuteQuery(queryString, reflexEntity), t, reflexEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultType.FAIL;
        }

    }

    public List<T> queryAll() {
    //    ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        try {
            baseQuery = new QueryALL<>(reflexEntity, t);


            String querysql = baseQuery.getSQLCreater().toSQL(t);


            return executeQueryBySQL(querysql);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<T> queryAll(String... condition) {
        try {
            ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
            //condition  需要重新设计一个类用来存储 这些 条件
            baseQuery = new QueryALL<>(reflexEntity, t);
            String querysql = baseQuery.getSQLCreater().toSQL(t);
            return executeQueryBySQL(querysql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<T> queryList(int start, int end) {

        return getQueryBuilder().callQuery().queryAll().where(Where.handle().limit(start, end)).executeQueryList();

    }


    public T queryByEntity(T t) {
      //  ReflexEntity reflexEntity = getReflexEntity();
        String sql = null;
        try {
            baseQuery = new QueryByEntity<>(reflexEntity, t);
            sql = baseQuery.getSQLCreater().toSQL(t);

        } catch (Exception e) {
            e.printStackTrace();
        }

        DebugLog.e(sql);
        return executeQuery(sql, t, reflexEntity);
    }

    public List<T> queryListByEntity(T t) {
       // ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        String sql = null;
        try {

            baseQuery = new QueryByEntity<>(reflexEntity, t);
            sql = baseQuery.getSQLCreater().toSQL(t);
            return executeQueryBySQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
            DebugLog.e(e.getMessage());
            return null;
        }

    }

    public T queryById(int id) {

        return queryById(Integer.toString(id));
    }

    public T queryById(String id) {
        try {
           // ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
            baseQuery = new QueryById<>(reflexEntity, t);
            String querySql = baseQuery.getSQLCreater().toSQL(id);
            return executeQuery(querySql, t, reflexEntity);
        } catch (Exception e) {
            DebugLog.e(e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    public abstract T getQueryEntity();

    public T executeQuery(String sql, T t, ReflexEntity reflexEntity) {
        DebugLog.e(sql);
        try {
            if (baseQuery == null) {
                baseQuery = new QueryALL<>(reflexEntity, t);
                baseQuery.getResultParse().useAlias(false);
            }
            Object cursor = databaseexcute.excuteQuery(sql, reflexEntity);
            if (cursor == null) return null;

            return baseQuery.getResultParse().parse(cursor, t, reflexEntity);

        } catch (Exception e) {
            e.printStackTrace();
            DebugLog.e(e.getMessage());
            return null;
        }

    }


    public T executeQuery(String sql) {
        DebugLog.e(sql);

        try {
          //  ReflexEntity reflexEntity = getReflexEntity();
            T t1 = getQueryEntity();
            if (baseQuery == null) {
                baseQuery = new QueryALL<>(reflexEntity, t);
                baseQuery.getResultParse().useAlias(false);
            }


            return baseQuery.getResultParse().parse(databaseexcute.excuteQuery(sql, reflexEntity), t1, reflexEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<T> executeQueryBySQL(String sql) {

    //    ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);

        DebugLog.e(sql);

        try {
            Object obj = databaseexcute.excuteQuery(sql, null);
            long timer2 = System.currentTimeMillis();

            List<T> list = baseQuery.getResultParse().parseList(obj, t, reflexEntity);

            long timer3 = System.currentTimeMillis() - timer2;

            DebugLog.e("timer3=" + timer3);

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            DebugLog.e(e.getMessage());
        }
        return null;
    }

    public List<T> executeQueryList(String sql) {

       // ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);

        DebugLog.e(sql);

        try {
            Object obj = databaseexcute.excuteQuery(sql, null);
            long timer2 = System.currentTimeMillis();
            if (baseQuery == null) {
                baseQuery = new QueryALL<>(reflexEntity, t);
            }
            List<T> list = baseQuery.getResultParse().useAlias(false).parseList(obj, t, reflexEntity);

            long timer3 = System.currentTimeMillis() - timer2;

            DebugLog.e("timer3=" + timer3);

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            DebugLog.e(e.getMessage());
        }
        return null;
    }

    public int executeUpadate(String sql) {
        // TODO Auto-generated method stub
        int result = databaseexcute.excuteUpdate(sql);
        DebugLog.e(sql);
        DebugLog.e("result=" + result + "     success=" + ResultType.SUCCESS + "  fail=" + ResultType.FAIL);
        return result;
    }

    private int executeUpadate(MySqliteStatement sql) {
        // TODO Auto-generated method stub
        int result = databaseexcute.excuteUpdate(sql);
        DebugLog.e(sql.getSql());
        DebugLog.e("result=" + result + "     success=" + ResultType.SUCCESS + "  fail=" + ResultType.FAIL);
        return result;
    }


    private int executeUpadateNoOpenTrancation(String sql) {
        // TODO Auto-generated method stub
        int result = databaseexcute.excuteUpdate(sql);
        DebugLog.e(sql);
        DebugLog.e("result=" + result + "     success=" + ResultType.SUCCESS + "  fail=" + ResultType.FAIL);
        return result;
    }

    private int executeUpadateNoOpenTrancation(MySqliteStatement sql) {
        // TODO Auto-generated method stub
        int result = databaseexcute.excuteUpdate(sql);
        DebugLog.e(sql.getSql());
        DebugLog.e("result=" + result + "     success=" + ResultType.SUCCESS + "  fail=" + ResultType.FAIL);
        return result;
    }

    public int drop() {
        return executeUpadate(tableInterface.drop(getReflexEntity()));
    }
}
