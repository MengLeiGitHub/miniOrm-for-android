package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.OnItemClick;
import com.example.bean.Teacher;
import com.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017-04-08.
 */

public class TecherAdpter extends BaseAdapter {
    List<Teacher> arrayList;
    private Context context;
    OnItemClick<Teacher> onItemClick;

    public void setOnItemClick(OnItemClick<Teacher> onItemClick) {
        this.onItemClick = onItemClick;
    }

    public TecherAdpter(Context context, List<Teacher> arrayList){
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
        final Teacher teacher= (Teacher) getItem(position);
        View  view= LayoutInflater.from(context).inflate(R.layout.text_item,null);
        TextView textView= (TextView) view.findViewById(R.id.name);
        textView.setText(teacher.getName());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.ItemClick(teacher);
            }
        });
        return view;
    }


    public void setList(List<Teacher> list) {
        this.arrayList = list;
        notifyDataSetChanged();
    }
}
