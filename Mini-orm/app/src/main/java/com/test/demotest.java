package com.test;

import android.test.AndroidTestCase;
import android.util.Log;

import com.test.test.Student;

import java.util.HashMap;

/**
 * Created by admin on 2016/9/27.
 */
public class demotest extends AndroidTestCase {

    public  void  test(){

        HashMap<String , Student >  hashMap=new HashMap<String , Student >();
            Student student=new Student();
        student.setAge(
                20
        );
        hashMap.put("s", student);
        student.setStuName("adasdas");

        Student sss=hashMap.get("s");
        Log.e("tag", sss.getStuName());
        StringBuilder  stringBuilder=new StringBuilder("limit  0 offset 10");
       char s= stringBuilder.charAt(stringBuilder.length()-1);
        stringBuilder.delete(0,stringBuilder.length());

        System.out.println(stringBuilder.toString()+"=====" +s);



    }

}
