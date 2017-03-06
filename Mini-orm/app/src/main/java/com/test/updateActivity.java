package com.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.miniorm.dao.builder.Where;
import com.miniorm.debug.DebugLog;
import com.test.test.CardDao;
import com.test.test.CustomerBeanDao;
import com.test.test.StuDao;
import com.test.test.Teacher;
import com.test.test.TeacherDao;
import com.test.test.TestBeanDao;

import java.util.ArrayList;
import java.util.List;

public class updateActivity extends Activity implements OnClickListener {


    ListView listview;
    TeacherAndStudentAdapter myBaseAdapter;
    TestBeanDao testBeanDao;
    TeacherDao teacherDao ;
    CardDao cardDao;
    StuDao stuDao ;
    CustomerBeanDao customerBeanDao;
    AlertDialog alertDialog;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        listview = (ListView) this.findViewById(R.id.listview);



        myBaseAdapter = new TeacherAndStudentAdapter();
        myBaseAdapter.setActivity(this);
        myBaseAdapter.setList(new ArrayList());
        DebugLog.isDebug=true;
        listview.setAdapter(myBaseAdapter);



        teacherDao=new TeacherDao();
        stuDao=new StuDao();

        this.findViewById(R.id.queryAll).setOnClickListener(this);

        this.findViewById(R.id.update).setOnClickListener(this);

        editText = (EditText) findViewById(R.id.val);
        editText.setText("小明");


    }

    int id = 1;

    public void onClick(View v) {
        // TODO Auto-generated method stub



        switch (v.getId()) {

            case R.id.queryAll:

                myBaseAdapter.setList(new ArrayList());
                Teacher teacher=  teacherDao.getQueryBuilder().callQuery().queryAll().where(Where.handle().eq("userid",2)).excute();
                List listaaa =new ArrayList();
                listaaa.add(teacher);
                myBaseAdapter.setList(listaaa);

                break;

            case R.id.update:

                 int i= teacherDao.executeUpadate("update  "+teacherDao.getReflexEntity().getTableName() +"  set   userName='"+editText.getText().toString()+"' where  userid=2" );
                onClick(this.findViewById(R.id.queryAll));

                break;
            default:


                break;
        }


    }


}
