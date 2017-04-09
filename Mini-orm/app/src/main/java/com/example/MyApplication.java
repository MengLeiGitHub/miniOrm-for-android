package com.example;

import android.app.Application;

import com.miniorm.MiniOrm;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by admin on 2017-04-05.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        MiniOrm.init(this,1,"test.db");
    }
}
