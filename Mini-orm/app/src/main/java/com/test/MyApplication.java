package com.test;

import android.app.Application;

import com.miniorm.MiniOrm;

/**
 * Created by admin on 2016/9/28.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MiniOrm.init(this,1,"test.db");

    }
}
