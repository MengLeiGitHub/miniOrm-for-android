package com.miniorm.constant;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;



public class MiniOrmDataConfig {
    private static String name = "miniorm";
    Application application;

    public MiniOrmDataConfig(Application application) {
        this.application = application;
    }

    private static String getName() {
            /*UserBean userBean= UserUtils.getUserBean();
			if(userBean!=null){

				return 	name+userBean.getUsername();
			}*/
        return name;
    }


    public void save(String key, String value) {
        SharedPreferences mySharedPreferences = application.getSharedPreferences(getName(),
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();


    }

    public void saveBoolean(String key, boolean value) {

        SharedPreferences mySharedPreferences = application.getSharedPreferences(getName(),
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();


    }

    public void saveint(String key, int value) {

        SharedPreferences mySharedPreferences = application.getSharedPreferences(getName(),
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();


    }

    public boolean getBoolean(String key) {
        SharedPreferences mySharedPreferences = application.getSharedPreferences(getName(),
                Activity.MODE_PRIVATE);
        return mySharedPreferences.getBoolean(key, false);
    }


    public int getInt(String key) {

        SharedPreferences mySharedPreferences = application.getSharedPreferences(getName(),
                Activity.MODE_PRIVATE);
        return mySharedPreferences.getInt(key, 0);
    }


    public String get(String key) {
        SharedPreferences mySharedPreferences = application.getSharedPreferences(getName(),
                Activity.MODE_PRIVATE);
        return mySharedPreferences.getString(key, "");
    }

    public String get(String key, String defaultVal) {
        SharedPreferences mySharedPreferences = application.getSharedPreferences(name,
                Activity.MODE_PRIVATE);
        return mySharedPreferences.getString(key, defaultVal);
    }

}
