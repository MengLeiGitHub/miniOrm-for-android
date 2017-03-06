package com.test.test;

import com.miniorm.android.androidBaseDao;
import com.miniorm.android.androidBaseDao2;

/**
 * Created by admin on 2016-11-03.
 */
public class TestBeanDao extends androidBaseDao2<TestBean> {

    @Override
    public TestBean getQueryEntity() {
        return new TestBean();
    }
}
