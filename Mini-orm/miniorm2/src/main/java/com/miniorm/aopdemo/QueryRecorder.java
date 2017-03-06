package com.miniorm.aopdemo;

import com.miniorm.debug.*;

import java.util.HashMap;

/**
 * Created by admin on 2017-03-01.
 */

public class QueryRecorder {
    private  static HashMap<String,Integer> recorder=new HashMap<>();

    public static synchronized  boolean  isRecordered(String key,String val){
        com.miniorm.debug.DebugLog.e(key+val);
        boolean isflag=recorder.containsKey(key+val);
        if(!isflag){
            recorder.put(key,1);
        }else {
          //  int i=recorder.get(key).intValue();
           // recorder.put(key,(++));

        }
        return !isflag;


    }


}
