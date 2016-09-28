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
public class MyBaseAdapter extends BaseAdapter {

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


            tv.setText("id="+student.getId() +"\n"+
                    "name="+student.getStuName()+"\n"+
                    "age="+student.getAge());

            tv.setTextSize(10);

            tv.setTextColor(Color.RED);

            return tv;
        }else  if (object instanceof Teacher){

            Teacher student= (Teacher) object;
            TextView tv = null;

            tv = new TextView(activity);


            tv.setText(JSON.toJSONString(student));

            tv.setTextSize(10);

            tv.setTextColor(Color.RED);


            return tv;
        }

            return  null;

    }
}
