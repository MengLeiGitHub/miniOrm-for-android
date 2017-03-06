package com.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.miniorm.android.SQLHelper;
import com.miniorm.android.impl2.DatabaseExcute;
import com.miniorm.android.impl2.SaveImpl;
import com.miniorm.dao.utils.ResultType;
import com.miniorm.debug.DebugLog;
import com.test.test.Card;
import com.test.test.CardDao;
import com.test.test.CustomerBeanDao;
import com.test.test.StuDao;
import com.test.test.Student;
import com.test.test.Teacher;
import com.test.test.TeacherDao;
import com.test.test.TestBean;
import com.test.test.TestBeanDao;

import java.util.ArrayList;
import java.util.List;

public class saveActivity extends Activity implements OnClickListener {


    ListView listview;
    TeacherAndStudentAdapter myBaseAdapter;
    TestBeanDao testBeanDao;
    TeacherDao teacherDao;
    CardDao cardDao;
    StuDao stuDao;
    CustomerBeanDao customerBeanDao;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        listview = (ListView) this.findViewById(R.id.listview);

        testBeanDao = new TestBeanDao();
        teacherDao = new TeacherDao();
        cardDao = new CardDao();
        stuDao = new StuDao();
        customerBeanDao = new CustomerBeanDao();

        customerBeanDao.createTable();

        cardDao.createTable();
        testBeanDao.createTable();


        teacherDao.createTable();
        stuDao.createTable();

        myBaseAdapter = new TeacherAndStudentAdapter();
        myBaseAdapter.setActivity(this);
        myBaseAdapter.setList(new ArrayList());
        DebugLog.isDebug = true;
        listview.setAdapter(myBaseAdapter);


        //	stuDao.createTable();
        //teacherDao.createTable();

        this.findViewById(R.id.save).setOnClickListener(this);

        this.findViewById(R.id.save_1).setOnClickListener(this);


    }

    int id = 1;

    public void onClick(View v) {
        // TODO Auto-generated method stub


        switch (v.getId()) {
            case R.id.save:


                List<Student> students = new ArrayList<>();
                for (int i = 1; i < 10; i++) {
                    Student student1 = new Student();
                    student1.setAge(i);
                    Teacher teacher = new Teacher();
                    teacher.setId(2);
                    student1.setTeacher(teacher);
                    student1.setStuName("name" + i);
                    student1.setId(i + 1);
                    students.add(student1);
                }
                stuDao.saveOrUpdate(students);
                teacherDao.deleteAll();
                ArrayList<Teacher> teachers = new ArrayList<>();
                for (int n = 1; n < 3; n++) {
                    Teacher teacher = new Teacher();
                    teacher.setSex(n % 2 == 0 ? "女" : "男");
                    teacher.setUserName("name" + n);
                    teacher.setId(n);
                    Student s = new Student();
                    s.setId(1);
                    teacher.setStudent(s);
                    teacher.setIsGril(n % 2 == 0);
                    teacher.setPwd("pwd" + n);
                    teachers.add(teacher);
                }
                long timer1 = System.currentTimeMillis();
                teacherDao.saveOrUpdate(teachers);
                long timer12 = System.currentTimeMillis() - timer1;


                DebugLog.isDebug = false;

                int i = 0;
                SaveImpl.timer = 0;
                DatabaseExcute.timer = 0;


/*
                Log.e("tag","timer12="+timer12 +"  SaveImpl.timer="+ com.miniorm.android.impl.SaveImpl.timer

                        +"  DatabaseEcute.timer="+ DatabaseExcute.timer  +"  sqlhelper.timer="+ SQLHelper.timer
                );*/


                Log.e("tag", "timer12=" + timer12 + "  SaveImpl.timer=" + SaveImpl.timer + "  cacheTime=" + SaveImpl.Cache

                        + "  DatabaseEcute.timer=" + DatabaseExcute.timer + "  sqlhelper.timer=" + SQLHelper.timer
                );


                break;

            case R.id.save_1:
               /* new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long timer1 = System.currentTimeMillis();
                        DebugLog.isDebug=false;
                       SQLHelper.getInstance().beginTransaction();
                        try {
                            int flag = ResultType.SUCCESS;
                            for (int n = 1; n < 10000; n++) {
                                TestBean testBean = new TestBean();
                                testBean.setId(n);
                                testBean.setUsername("username" + n);
                                testBean.setCompany("commpany" + n);
                                int flag2 = testBeanDao.FastSave(testBean);
                              *//*  Card card = new Card();
                                card.setId(n);
                                card.setAddress("杭州幸福截取" + n);
                                card.setAlies("别名" + n);
                                int flag3 = cardDao.FastSave(card);
                                if (flag3 == ResultType.FAIL) {
                                    flag = flag3;
                                }
                                if (flag2 == ResultType.FAIL) {

                                    flag = flag2;
                                }*//*
                            }
                          //  if (flag == ResultType.SUCCESS) {
                                SQLHelper.getInstance().setTransactionSuccessful();
                          //  }


                        } catch (Exception e) {
                            e.printStackTrace();

                        } finally {
                          SQLHelper.getInstance().endTransaction();
                        }
                        Log.e("tag", "timer=======================" + (System.currentTimeMillis() - timer1));
                    }
                }).start();*/
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long timer1 = System.currentTimeMillis();
                        SQLHelper.getInstance().beginTransaction();
                         DebugLog.isDebug=false;
                        TeacherDao teacherDao1=new TeacherDao();
                        StuDao stuDao=new StuDao();
                        try {
                            int flag = ResultType.SUCCESS;
                            List<Student> students1 = new ArrayList<>();
                            for (int i1 = 1; i1 < 10000; i1++) {
                                Student student1 = new Student();
                                student1.setAge(i1);

                               // student1.setTeacher(teacher);
                                student1.setStuName("name" + i1);
                                student1.setId(i1 + 1);
                              //   students1.add(student1);
                                int  fla= stuDao.FastSave(student1);

                                Teacher teacher = new Teacher();
                                teacher.setId(i1);
                                teacher.setPwd("pwd123");
                                teacherDao1.FastSave(teacher);

                                if(fla==ResultType.FAIL){
                                    flag=fla;
                                }

                            }
                          // stuDao.saveOrUpdate(students1);
                            if (flag == ResultType.SUCCESS) {
                                  SQLHelper.getInstance().setTransactionSuccessful();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();

                        } finally {
                           SQLHelper.getInstance().endTransaction();
                        }
                        Log.e("tag", "time2r||||||||||||||||||||||=" + (System.currentTimeMillis() - timer1));
                    }
                }).start();

                break;

            default:


                break;
        }


    }


}
