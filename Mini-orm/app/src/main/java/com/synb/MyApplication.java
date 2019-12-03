package com.synb;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.facebook.stetho.Stetho;
import com.miniorm.MiniOrm;
import com.squareup.leakcanary.LeakCanary;
import com.synb.phonecall.IPermissionPhoneCallStrategy;
import com.synb.phonecall.PhoneCallActivity;
import com.test.R;

/**
 * Created by admin on 2017-04-05.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        MiniOrm.init(this,1,"test.db");
        Stetho.initializeWithDefaults(this);
        ScreenAdapter.setup(this);
        IPermissionPhoneCallStrategy.getInstance(this).setResouceLisenter(new IPermissionPhoneCallStrategy.GetResouceLisenter() {
            @Override
            public String getName(PhoneCallActivity.CallItem currentCallItem) {
                return currentCallItem.phone;
            }

            @Override
            public Drawable getAppIconDrawable() {
                return getResources().getDrawable(R.mipmap.ic_launcher);
            }

            @Override
            public int getIcon() {
                return R.mipmap.ic_launcher;
            }
        });
    }
}
