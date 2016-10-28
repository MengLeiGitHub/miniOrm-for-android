package com.test;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.miniorm.MiniOrm;
import com.test.test.TeacherDao;

/**
 * Created by admin on 2016/9/28.
 */
public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MiniOrm.init(this, 10 , "test.db");
        MiniOrm.addUpdateTable( TeacherDao.class);
    }
}
