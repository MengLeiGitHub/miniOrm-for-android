package com.miniorm.android;

import android.util.Log;

import com.miniorm.dao.utils.ResultType;

/**
 * Created by admin on 2017-04-09.
 */

public class TransactionManager {
    static String TAG=TransactionManager.class.getName();
    public interface TransactionCallBack{
        public int  doWork();
    }
    public interface ResultCallBack{
        public  void   workSucess();
        public void   workFail();
    }
    public  static   synchronized   void   doWork(TransactionCallBack transactionCallBack){
        if(transactionCallBack==null){
            try {
                throw new Exception("transactionCallBack  should  be null");
            } catch (Exception e) {
                Log.e(TAG,Log.getStackTraceString(e));
            }
        }
        SQLHelper.getInstance().beginTransaction();
        int result=transactionCallBack.doWork();
        if(result== ResultType.SUCCESS){
            SQLHelper.getInstance().setTransactionSuccessful();
        }
        SQLHelper.getInstance().endTransaction();
    }


    public     static synchronized  void     doWork(TransactionCallBack transactionCallBack,ResultCallBack resultCallBack){
        if(transactionCallBack==null){
            try {
                throw new Exception("transactionCallBack  should  be null");
            } catch (Exception e) {
                Log.e(TAG,Log.getStackTraceString(e));
            }
        }
        SQLHelper.getInstance().beginTransaction();
        int result=transactionCallBack.doWork();
        if(result== ResultType.SUCCESS){
            SQLHelper.getInstance().setTransactionSuccessful();
        }
        SQLHelper.getInstance().endTransaction();

        if(result== ResultType.SUCCESS){
            resultCallBack.workSucess();
        }else {
            resultCallBack.workFail();
        }
    }




}
