package com.miniorm.dao.reflex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by admin on 2017-02-20.
 */

public class MySqliteStatement {
    private String sql;
    private HashMap<String,Integer> keys;
    private HashMap<Integer,Object> val;


    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public HashMap<String, Integer> getKeys() {
        return keys;
    }

    public void setKeys(HashMap<String, Integer> keys) {
        this.keys = keys;
    }

    public HashMap<Integer, Object> getVal() {
        return val;
    }

    public void setVal(HashMap<Integer, Object> val) {
        this.val = val;
    }
}
