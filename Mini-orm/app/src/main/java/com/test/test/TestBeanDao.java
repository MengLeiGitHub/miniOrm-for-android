package com.test.test;

import com.miniorm.android.androidBaseDao;

/**
 * Created by admin on 2016-11-03.
 */
public class TestBeanDao extends androidBaseDao<TestBean> {

    @Override
    public TestBean getQueryEntity() {
        return new TestBean();
    }
}
