package com.synb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.synb.OnItemClick;
import com.synb.bean.Student;
import com.test.R;

import java.util.List;

/**
 * Created by admin on 2017-04-08.
 */

public class StudentAdpter extends BaseAdapter {
    List<Student> arrayList;
    private Context context;
    OnItemClick<Student> onItemClick;

    public void setOnItemClick(OnItemClick<Student> onItemClick) {
        this.onItemClick = onItemClick;
    }

    public StudentAdpter(Context context, List<Student> arrayList){
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
        final Student student= (Student) getItem(position);
        View  view= LayoutInflater.from(context).inflate(R.layout.text_item,null);
        TextView textView= (TextView) view.findViewById(R.id.name);
        textView.setText(student.getName());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.ItemClick(student);
            }
        });
        return view;
    }


    public void setList(List<Student> list) {
        this.arrayList = list;
        notifyDataSetChanged();
    }
}
