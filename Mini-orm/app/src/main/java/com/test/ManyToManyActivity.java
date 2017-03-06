package com.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.miniorm.dao.utils.StringUtils;
import com.miniorm.debug.DebugLog;
import com.test.test.StuDao;
import com.test.test.Student;
import com.test.test.StudentTeacher;
import com.test.test.StudentTeacherDao;
import com.test.test.Teacher;
import com.test.test.TeacherDao;

import java.util.ArrayList;
import java.util.List;

public class ManyToManyActivity extends Activity implements OnClickListener {


    ListView xueshenglistview,laoshilistview;
    TeacherAndStudentAdapter xueshengadapter,laoshiadapter;
     TeacherDao teacherDao ;
     StuDao stuDao ;
    StudentTeacherDao studentTeacherDao;
    AlertDialog alertDialog;
    EditText  xueshengId,laoshiId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manytomany);
        xueshenglistview = (ListView) this.findViewById(R.id.xueshenglistview);
        laoshilistview = (ListView) this.findViewById(R.id.laoshilistview);

         teacherDao = new TeacherDao();
         stuDao = new StuDao();
         studentTeacherDao=new StudentTeacherDao();





        teacherDao.createTable();
        stuDao.createTable();
        studentTeacherDao.createTable();


        xueshengadapter = new TeacherAndStudentAdapter();
        xueshengadapter.setActivity(this);
        xueshengadapter.setList(new ArrayList());

        laoshiadapter = new TeacherAndStudentAdapter();
        laoshiadapter.setActivity(this);
        laoshiadapter.setList(new ArrayList());
        laoshilistview.setAdapter(laoshiadapter);
        DebugLog.isDebug=true;
        xueshenglistview.setAdapter(xueshengadapter);



        this.findViewById(R.id.save).setOnClickListener(this);

        this.findViewById(R.id.queryAll).setOnClickListener(this);

        this.findViewById(R.id.laoshichaxun).setOnClickListener(this);

        laoshiId= (EditText) this.findViewById(R.id.laoshiid);
        xueshengId= (EditText) this.findViewById(R.id.xueshengid);



    }

    int id = 1;

    public void onClick(View v) {
        // TODO Auto-generated method stub



        switch (v.getId()) {
            case R.id.save:

                teacherDao.deleteAll();
                ArrayList<Teacher> teachers = new ArrayList<>();
                for (int n = 1; n < 4 ; n++) {
                    Teacher teacher = new Teacher();
                    teacher.setSex(n % 2 == 0 ? "女" : "男");
                    teacher.setUserName("name"+n);
                    teacher.setId(n);
                  /*  Student s=     new Student();
                    s.setId(1);
                    teacher.setStudent(s);*/
                    teacher.setIsGril(n % 2 == 0);
                    teacher.setPwd("pwd" + n);
                    teachers.add(teacher);
                }
                long timer1=System.currentTimeMillis();
                teacherDao.saveOrUpdate(teachers);
                List<Student> students=new ArrayList<>();

                List<StudentTeacher> studentTeachers=new ArrayList<>();


                for (int n = 1; n < 4 ; n++) {
                    Student student = new Student();
                    student.setAge(n);
                    student.setId(n);
                    student.setStuName("学生"+n);
                    students.add(student);

                    for (int i=n;i<4;i++){
                        StudentTeacher studentTeacher=new StudentTeacher();
                        studentTeacher.setStudent(student);
                        Teacher teacher = new Teacher();
                        teacher.setId(i);
                        studentTeacher.setTeacher(teacher);
                        studentTeachers.add(studentTeacher);

                    }


                }
                stuDao.saveOrUpdate(students);
                studentTeacherDao.saveOrUpdate(studentTeachers);




                break;
            case R.id.queryAll:
                 String id=   xueshengId.getText().toString();
                if(StringUtils.isNull(id)){
                    Toast.makeText(this,"id=不i能为空",Toast.LENGTH_LONG).show();
                    return;
                }
               Student student= stuDao.queryById(id);
               List  list= student.holderTeachers();
                xueshengadapter.setList(list);

                break;
            case R.id.laoshichaxun:

                 id=   laoshiId.getText().toString();
                if(StringUtils.isNull(id)){
                    Toast.makeText(this,"id=不i能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                Teacher teacher= teacherDao.queryById(id);
                List  list1= teacher.holderStudents();
                laoshiadapter.setList(list1);

                break;
            default:


                break;
        }


    }


}
