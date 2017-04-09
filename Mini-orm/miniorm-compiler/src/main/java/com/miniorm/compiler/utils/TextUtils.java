package com.miniorm.compiler.utils;

/**
 * Created by admin on 2017-03-29.
 */

public class TextUtils {
    public static boolean  isEmpty(String text){
        return text==null || "".equals(text.trim());
    }

    public static  boolean notEmpty(String text){

        return !isEmpty(text);
    }
}
