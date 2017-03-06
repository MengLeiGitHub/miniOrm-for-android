package com.miniorm.dao.reflex;

import com.miniorm.entity.KV;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017-02-20.
 */

public class MySqliteStatement {
    private String sql;
    private List<KV> kvlist;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<KV> getKvlist() {
        return kvlist;
    }

    public void setKvlist(List<KV> kvlist) {
        this.kvlist = kvlist;
    }
}
