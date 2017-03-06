package com.test;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.alibaba.fastjson.JSON;
import com.test.test.Student;
import com.test.test.Teacher;

import java.util.List;

/**
 * Created by admin on 2016/9/23.
 */
public class TeacherAndStudentAdapter extends BaseAdapter {

    List list;
    Activity activity;
    public void  setList(    List list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        if(list==null)return 0;
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object object=getItem(position);
        if(object instanceof Student){
            Student student=  (Student) object;
            TextView tv = null;

            tv = new TextView(activity);
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("学生姓名：");
            stringBuilder.append(student.getStuName());
            stringBuilder.append("学生的年龄：");
            stringBuilder.append(student.getAge());

            stringBuilder.append("学生的id：");
            stringBuilder.append(student.getId());

            tv.setText(stringBuilder.toString());

            tv.setTextSize(10);

            tv.setTextColor(Color.RED);

            return tv;
        }else  if (object instanceof Teacher){

            Teacher student= (Teacher) object;
            TextView tv = null;

            tv = new TextView(activity);
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("老师姓名：");
            stringBuilder.append(student.getUserName());
            stringBuilder.append("老师密码：");
            stringBuilder.append(student.getPwd());

            stringBuilder.append("老师的性别：");
            stringBuilder.append(student.getSex());

            stringBuilder.append("老师的id：");
            stringBuilder.append(student.getId());


            tv.setTextSize(10);

            tv.setTextColor(Color.GREEN);
            tv.setText(stringBuilder.toString());


            return tv;
        }

            return  null;

    }
}
