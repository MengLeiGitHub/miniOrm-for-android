package com.miniorm;

import android.app.Application;

/**
 * Created by admin on 2016/9/27.
 */
public class MiniOrm {
        public static  Application application;
        public static  int   version;
        public static  String   dbName;

        public  static  void  init(Application application,int versionCode,String dbName){
            MiniOrm.application=application;
            MiniOrm.dbName=dbName;
            MiniOrm.version=versionCode;
        }
}
