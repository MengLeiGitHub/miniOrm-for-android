package com.synb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.synb.OnItemClick;
import com.synb.bean.Course;
import com.test.R;

import java.util.List;

/**
 * Created by admin on 2017-04-08.
 */

public class LessionAdpter extends BaseAdapter {
    List<Course> arrayList;
    private Context context;
    OnItemClick<Course> onItemClick;

    public void setOnItemClick(OnItemClick<Course> onItemClick) {
        this.onItemClick = onItemClick;
    }

    public LessionAdpter(Context context, List<Course> arrayList){
        this.arrayList=arrayList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Course course= (Course) getItem(position);
        View  view= LayoutInflater.from(context).inflate(R.layout.text_item,null);
        TextView textView= (TextView) view.findViewById(R.id.name);
        textView.setText(course.getcName());
        return view;
    }


    public void setList(List<Course> list) {
        this.arrayList = list;
        notifyDataSetChanged();
    }
}
