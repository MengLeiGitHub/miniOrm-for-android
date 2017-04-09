package com.miniorm.query.analysis;

/**
 * Created by ML on 2017-02-15.
 */

public class SQL {
    private String sql;
    public SQL(String sql){
        this.sql=sql;
    }

    public String toSQL() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return sql;
    }
}
