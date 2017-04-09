package com.example.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.OnItemClick;
import com.example.bean.SchoolClass;
import com.example.bean.Teacher;

import java.util.List;

/**
 * Created by admin on 2017-04-08.
 */

public class SchoolClassAdpter extends BaseAdapter {
    List<SchoolClass> arrayList;
    private Context context;
    OnItemClick<SchoolClass> onItemClick;

    public void setOnItemClick(OnItemClick<SchoolClass> onItemClick) {
        this.onItemClick = onItemClick;
    }

    public SchoolClassAdpter(Context context, List<SchoolClass> arrayList){
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
        final SchoolClass  schoolClass= (SchoolClass) getItem(position);
        TextView textView=new TextView(context);
        textView.setText(schoolClass.getsClassName());

        return textView;
    }




}
