package com.miniorm.dao;




import com.miniorm.dao.builder.QueryBuilder;
import com.miniorm.dao.builder.Where;
import com.miniorm.dao.database.BaseResultParseInterface;
import com.miniorm.dao.database.DeleteInterface;

import com.miniorm.dao.database.TableInterface;
import com.miniorm.dao.database.DatabaseExeInterface;
import com.miniorm.dao.database.SaveInterface;
import com.miniorm.dao.database.UpdateInterface;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.MySqliteStatement;
import com.miniorm.dao.reflex.ProxyCache;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.dao.utils.Content;
import com.miniorm.dao.utils.ResultType;
import com.miniorm.debug.DebugLog;
import com.miniorm.query.BaseQuery;
import com.miniorm.query.QueryALL;
import com.miniorm.query.QueryByEntity;
import com.miniorm.query.QueryById;
import com.miniorm.query.QueryLastInsertRowId;
import com.miniorm.query.parse.BaseResultParse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class BaseDao<T> {

    private SaveInterface saveInterface;

    private UpdateInterface updateInterface;

    private BaseQuery<T, Object> baseQuery;

    private TableInterface tableInterface;

    private BaseResultParseInterface resultParse;

    private DatabaseExeInterface databaseexcute;

    private DeleteInterface deleteInterface;

    String tag = this.getClass().getName();
    Class<T> tEntityClass;
    T tEntity = null;
    private QueryBuilder<T> queryBuilder;
    ReflexEntity reflexEntity;
    public BaseDao() {
        tEntityClass = getTableEntitys();
        EntityParse entityParse = new EntityParse(tEntityClass);
        reflexEntity = entityParse.getFieldValueFromT(tEntity);
        ReflexCache.addReflexEntity(tEntityClass.getName(), reflexEntity);
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
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(tEntityClass.getName());
        String sql = null;
        try {
            sql = tableInterface.create(tEntityClass, reflexEntity);
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


    public <S extends BaseDao<T>> S setBaseQuery(BaseQuery<T, Object> baseQuery) {
        this.baseQuery = baseQuery;
        return (S) this;
    }


    public QueryBuilder<T> getQueryBuilder() {
        if (queryBuilder == null) queryBuilder = new QueryBuilder<T>(this);
        return queryBuilder;
    }


    public ReflexEntity getReflexEntity() {
        return ReflexCache.getReflexEntity(tEntityClass.getName());
    }


    public void setDatabaseexcute(DatabaseExeInterface databaseexcute) {
        this.databaseexcute = databaseexcute;
    }


    public int FastSave(T t) throws IllegalAccessException {
        MySqliteStatement saveString = saveInterface.save(t, reflexEntity);
        return executeUpadateNoOpenTrancation(saveString);
    }


    public int save(T t) {
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
        MySqliteStatement saveString = saveInterface.save(t, reflexEntity);
        return executeUpadate(saveString);
    }

    public int FastSaveOrUpdate(T t) throws IllegalAccessException {
        ReflexEntity reflexEntity = ReflexCache.getReflexEntity(t.getClass().getName());
        MySqliteStatement saveString = saveInterface.saveOrUpdate(t, reflexEntity);
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


    @Deprecated
    public int save(List<T> t) {

        int result = 0;
        if (t == null || t.size() == 0) {

            return ResultType.FAIL;
        }
        try {
            databaseexcute.beginTransaction();
            for (T tt : t) {
                MySqliteStatement saveString = saveInterface.save(tt, reflexEntity);
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
        try {

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
        }

        return result;
    }


    public int update(T t) {

        MySqliteStatement updatesql = null;
        try {
            updatesql = updateInterface.update(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return executeUpadate(updatesql.getSql());

    }

    public int delete(T t) {
        String deleteSql = null;
        try {
            deleteSql = deleteInterface.delete(t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return executeUpadate(deleteSql);
    }

    public int delete(T t, String... condition) {

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

        //  ReflexEntity reflexEntity = new EntityParse<T>(tEntityClass).getFieldValueFromT(tEntityClass);
        String deleteSql = deleteInterface.deleteAll(tEntityClass, reflexEntity);
        return executeUpadate(deleteSql);
    }

    public int queryLastInsertId() {
        try {
            ReflexEntity reflexEntity = ReflexCache.getReflexEntity(tEntityClass.getName());
            baseQuery = new QueryLastInsertRowId<>(reflexEntity, tEntityClass);
            String queryString = baseQuery.getSQLCreater().toSQL(tEntity);
            BaseResultParse baseResultParse = baseQuery.getResultParse();
            return baseResultParse.ParseLastInsertRowId(databaseexcute.excuteQuery(queryString, reflexEntity), tEntityClass, reflexEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultType.FAIL;
        }

    }

    public List<T> queryAll() {
        try {
            baseQuery = new QueryALL<>(reflexEntity, tEntityClass);


            String querysql = baseQuery.getSQLCreater().toSQL(tEntity);


            return executeQueryBySQL(querysql);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<T> queryAll(String... condition) {
        try {
            ReflexEntity reflexEntity = ReflexCache.getReflexEntity(tEntityClass.getName());
            //condition  需要重新设计一个类用来存储 这些 条件
            baseQuery = new QueryALL<>(reflexEntity, tEntityClass);
            String querysql = baseQuery.getSQLCreater().toSQL(tEntity);
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
        String sql = null;
        try {
            baseQuery = new QueryByEntity<>(reflexEntity, this.tEntityClass);
            sql = baseQuery.getSQLCreater().toSQL(t);

        } catch (Exception e) {
            e.printStackTrace();
        }

        DebugLog.e(sql);
        return executeQuery(sql, reflexEntity);
    }

    public List<T> queryListByEntity(T t) {
        String sql = null;
        try {

            baseQuery = new QueryByEntity<>(reflexEntity, this.tEntityClass);
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
            baseQuery = new QueryById<>(reflexEntity, tEntityClass);
            String querySql = baseQuery.getSQLCreater().toSQL(id);
            return executeQuery(querySql, reflexEntity);
        } catch (Exception e) {
            DebugLog.e(e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    private Class<T> getTableEntitys() {
        queryEntityClassName=getQueryEntityClassName();
        Class superClass = ProxyCache.getProxyClass(queryEntityClassName);
        if (superClass == null) {
            tEntity = getTableEntity();
            tEntityClass= tEntity==null?tEntityClass: (Class<T>) tEntity.getClass();
            try {
                Class tclass = Class.forName(tEntityClass.getName() + Content.NEW_CLASS_NAME);
                tEntityClass = tclass;
            } catch (Exception e) {

            }

            try {
                tEntity=tEntity==null?tEntityClass.newInstance():tEntity;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            ProxyCache.addProxyClass(queryEntityClassName, tEntityClass);
        }else {
            try {
                tEntity= (T) superClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            tEntityClass= superClass;
        }
        return tEntityClass;
    }
    private String queryEntityClassName;
    public final String  getQueryEntityClassName(){
        if(queryEntityClassName==null){
            Class superClass;
            Type t1 = getClass().getGenericSuperclass();
            if (t1 instanceof ParameterizedType) {
                Type[] p = ((ParameterizedType) t1).getActualTypeArguments();
                superClass = (Class<T>) p[0];
                return superClass.getName();
            }
            return null;
        }else {
            return queryEntityClassName;
        }


    }

    public T getTableEntity() {
        Class superClass;
        Type t1 = getClass().getGenericSuperclass();
        if (t1 instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t1).getActualTypeArguments();
            superClass = (Class<T>) p[0];
            tEntityClass = superClass;
        }
        try {
            return tEntityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T executeQuery(String sql, ReflexEntity reflexEntity) {
        DebugLog.e(sql);
        try {
            if (baseQuery == null) {
                baseQuery = new QueryALL<>(reflexEntity, this.tEntityClass);
                baseQuery.getResultParse().useAlias(false);
            }
            Object cursor = databaseexcute.excuteQuery(sql, reflexEntity);
            if (cursor == null) return null;

            return baseQuery.getResultParse().parse(cursor, this.tEntityClass, reflexEntity);

        } catch (Exception e) {
            e.printStackTrace();
            DebugLog.e(e.getMessage());
            return null;
        }

    }


    public T executeQuery(String sql) {
        DebugLog.e(sql);
        try {
            if (baseQuery == null) {
                baseQuery = new QueryALL<>(reflexEntity, tEntityClass);
                baseQuery.getResultParse().useAlias(false);
            }
            return baseQuery.getResultParse().parse(databaseexcute.excuteQuery(sql, reflexEntity), tEntityClass, reflexEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<T> executeQueryBySQL(String sql) {
        DebugLog.e(sql);
        try {
            Object obj = databaseexcute.excuteQuery(sql, null);
            List<T> list = baseQuery.getResultParse().parseList(obj, tEntityClass, reflexEntity);

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            DebugLog.e(e.getMessage());
        }
        return null;
    }

    public List<T> executeQueryList(String sql) {
        DebugLog.e(sql);
        try {
            Object obj = databaseexcute.excuteQuery(sql, null);
            if (baseQuery == null) {
                baseQuery = new QueryALL<>(reflexEntity, tEntityClass);
            }
            List<T> list = baseQuery.getResultParse().useAlias(false).parseList(obj, tEntityClass, reflexEntity);
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
    private long save(MySqliteStatement sql) {
        // TODO Auto-generated method stub
        long result = databaseexcute.excuteInsert(sql);
        DebugLog.e(sql.getSql());
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
