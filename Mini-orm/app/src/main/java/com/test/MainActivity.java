package com.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.miniorm.MiniOrm;
//import com.miniorm.aopdemo.DebugTrace;
import com.miniorm.aopdemo.DebugTrace;
import com.miniorm.debug.DebugLog;
import com.test.test.CardDao;
import com.test.test.CustomerBeanDao;
import com.test.test.ModelBean;
import com.test.test.StuDao;
import com.test.test.TeacherDao;
import com.test.test.TestBeanDao;

//import aopdemo.DebugTrace;




public class MainActivity extends Activity implements OnClickListener {


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
        setContentView(R.layout.activity_main);


        this.findViewById(R.id.chaxun).setOnClickListener(this);
        this.findViewById(R.id.baocun).setOnClickListener(this);
        this.findViewById(R.id.gengxin).setOnClickListener(this);

        this.findViewById(R.id.shanchu).setOnClickListener(this);
        this.findViewById(R.id.duoduoduo).setOnClickListener(this);


        MiniOrm.init(getApplication(), 8,  "123.db");


    }

    int id = 1;

    public void onClick(View v) {
        // TODO Auto-generated method stub


        switch (v.getId()) {
            case R.id.chaxun:
                Intent intent=new Intent(this,queryActivity.class);
                startActivity(intent);
                break;
            case R.id.baocun:
                intent=new Intent(this,saveActivity.class);
                startActivity(intent);
                break;
            case R.id.gengxin:
                intent=new Intent(this,updateActivity.class);
                startActivity(intent);
                break;

            case R.id.shanchu:
              //  shanchu();
                long  timr1=System.currentTimeMillis();
                //new Teacher().getStudent();
                  new ModelBean().getList();
                DebugLog.e((System.currentTimeMillis()-timr1)+"");
                break;

            case R.id.duoduoduo:
                intent=new Intent(this,ManyToManyActivity.class);
                startActivity(intent);
                break;



            case R.id.drop:

            case R.id.create:

                break;

            case R.id.queryBuilder:


                break;
            default:


                break;
        }


    }
    /**
     * aop 统计
     */
    @DebugTrace
    public void shanchu()
    {
        //  Toast.makeText(this,"132",Toast.LENGTH_LONG).show();
       SystemClock.sleep(3000);
    }



}
