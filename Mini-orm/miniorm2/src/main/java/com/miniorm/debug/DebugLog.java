package com.miniorm.debug;

import android.util.Log;

/**
 * Created by admin on 2016/10/24.
 */
public class DebugLog {
    public static  boolean isDebug=true;
    public static void e(String msg){
        if(isDebug)
        Log.e("tag",msg);
    }

}
