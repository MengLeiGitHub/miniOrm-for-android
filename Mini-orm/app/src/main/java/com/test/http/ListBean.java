package com.test.http;

import java.util.List;

/**
 * Created by admin on 2016-11-22.
 */
public class ListBean<T> {
    public List<T>  list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
