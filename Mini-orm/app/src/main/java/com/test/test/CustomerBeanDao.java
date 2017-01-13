package com.test.test;

import com.miniorm.android.androidBaseDao;

/**
 * Created by admin on 2016-11-28.
 */

public class CustomerBeanDao  extends androidBaseDao<CustomerBean> {


    @Override
    public CustomerBean getQueryEntity() {
        return new CustomerBean();
    }
}
