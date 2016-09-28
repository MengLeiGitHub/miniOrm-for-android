package com.miniorm.dao;

import android.util.Log;

import com.miniorm.android.parseType.ParseTypeFactory;
import com.miniorm.dao.database.BaseResultParseInterface;
import com.miniorm.dao.database.DeleteInterface;
import com.miniorm.dao.database.QueryInterface;
import com.miniorm.dao.database.TableInterface;
import com.miniorm.dao.database.UpdateInterface;
import com.miniorm.dao.utils.EntityParse;
import com.miniorm.dao.utils.ReflexEntity;
import com.miniorm.dao.database.DatabaseExeInterface;
import com.miniorm.dao.database.SaveInterface;

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
        if(t==null)  t= getQueryEntity();
        ParseTypeFactory.addEntityParse(t.getClass().getName(),this);
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
        T  t=getQueryEntity();
        ReflexEntity reflexEntity = new EntityParse<T>(t)
                .getFieldValueFromT(t);
        String sql = tableInterface.create(reflexEntity);

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

        ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);
        String saveString = saveInterface.save(t, reflexEntity);
        int flag= executeUpadate(saveString);
        if(flag==0)return flag;

        flag=  queryLastInsertId();
        return flag;
    }

    public int save(List<T> t) {
        ReflexEntity reflexEntity = new EntityParse<T>(t.get(0)).getFieldValueFromT(t);

        String saveString = saveInterface.save(t, reflexEntity);

        return executeUpadate(saveString);
    }

    public int update(T t) {
        ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);
        String updatesql = updateInterface.update(t, reflexEntity);


        return executeUpadate(updatesql);

    }

    public int delete(T t) {
        ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);
        String deleteSql = deleteInterface.delete(t, reflexEntity);

        return executeUpadate(deleteSql);
    }

    public int delete(T t, String... condition) {
        ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);
        reflexEntity.setCondition(condition);
        String deleteSql = deleteInterface.delete(t, reflexEntity);
        Log.e(tag, deleteSql);
        return executeUpadate(deleteSql);
    }

    public int deleteAll() {

        ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);
        String deleteSql = deleteInterface.deleteAll(t, reflexEntity);
        return executeUpadate(deleteSql);
    }


    public int queryLastInsertId(){
        ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);
        String queryString = queryInterface.queryLastInsertRowId(t, reflexEntity);

        return   resultParse.ParseLastInsertRowId(
                databaseexcute.excuteQuery(queryString, reflexEntity), t, reflexEntity);
    }

    public List<T> queryAll() {
        EntityParse<T> entityParse = new EntityParse<T>(t);
        ReflexEntity reflexEntity = entityParse
                .getFieldValueFromT(getQueryEntity());

        String querysql = queryInterface.queryAll(reflexEntity);


        return executeQueryList(querysql);
    }

    public List<T> queryAll(String... condition) {
        EntityParse<T> entityParse = new EntityParse<T>(t);
        ReflexEntity reflexEntity = entityParse
                .getFieldValueFromT(t);
        reflexEntity.setCondition(condition);

        String querysql = queryInterface.queryAll(reflexEntity);


        return executeQueryList(querysql);
    }


    public T queryByEntity(T t) {
        EntityParse<T> entityParse = new EntityParse<T>(t);
        ReflexEntity reflexEntity = entityParse.getFieldValueFromT(t);
        String sql = queryInterface.queryByEntity(t, reflexEntity);

        Log.e("tag=====", sql);
        return executeQuery(sql, t, reflexEntity);
    }

    public T queryById(int id) {
        T t = getQueryEntity();
        EntityParse<T> entityParse = new EntityParse<T>(t);
        ReflexEntity reflexEntity = entityParse.getFieldValueFromT(t);
        String querySql = queryInterface.queryById(id, reflexEntity);
        System.out.println(querySql);
        return executeQuery(querySql, t, null);
    }
    public T queryById(String id) {
        T t = getQueryEntity();
        EntityParse<T> entityParse = new EntityParse<T>(t);
        ReflexEntity reflexEntity = entityParse.getFieldValueFromT(t);
        String querySql = queryInterface.queryById(id, reflexEntity);
        System.out.println(querySql);
        return executeQuery(querySql, t, null);
    }

    public abstract T getQueryEntity();

    // public abstract List<T> getEntityList();

    public T executeQuery(String sql, T t, ReflexEntity reflexEntity) {

        Log.e(tag, sql);




        return (T) resultParse.parse(
                databaseexcute.excuteQuery(sql, reflexEntity), t, reflexEntity);

    }

    public List<T> executeQueryList(String sql) {

        ReflexEntity reflexEntity = new EntityParse<T>(t).getFieldValueFromT(t);

        Log.e(tag, sql);

        try {
            return (List<T>) resultParse.parseList(
                    databaseexcute.excuteQuery(sql, null),t, reflexEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;

    }

    private int executeUpadate(String sql) {
        // TODO Auto-generated method stub
        if (databaseexcute == null)
            return 0;
        Log.e(tag, sql);

        return databaseexcute.excuteUpdate(sql);
    }

    public int drop() {
        T t = getQueryEntity();
        return databaseexcute.excuteUpdate(tableInterface.drop(new EntityParse<T>(t).getFieldValueFromT(t)));
    }
}
