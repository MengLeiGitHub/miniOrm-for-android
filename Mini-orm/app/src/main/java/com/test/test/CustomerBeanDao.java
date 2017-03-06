package com.test.test;

import com.miniorm.android.androidBaseDao;
import com.miniorm.android.androidBaseDao2;

/**
 * Created by admin on 2016-11-28.
 */

public class CustomerBeanDao  extends androidBaseDao2<CustomerBean> {


    @Override
    public CustomerBean getQueryEntity() {
        return new CustomerBean();
    }
}
