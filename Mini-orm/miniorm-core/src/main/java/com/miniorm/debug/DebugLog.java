package com.miniorm.debug;

import android.util.Log;

import com.miniorm.MiniOrm;

/**
 * Created by admin on 2016/10/24.
 */
public class DebugLog {
    public static  boolean isDebug=true;
    private static String TAG= MiniOrm.class.getSimpleName();
    public static void e(String msg){
        if(isDebug)
        Log.e(TAG,msg);
    }
    public static  void  e(Throwable throwable){
        Log.e(TAG,Log.getStackTraceString(throwable).toString());
    }



}
