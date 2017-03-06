package com.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.miniorm.dao.builder.Where;
import com.miniorm.debug.DebugLog;
import com.test.test.CardDao;
import com.test.test.CustomerBeanDao;
import com.test.test.StuDao;
import com.test.test.Student;
import com.test.test.Teacher;
import com.test.test.TeacherDao;
import com.test.test.TestBeanDao;

import java.util.ArrayList;
import java.util.List;

public class queryActivity extends Activity implements OnClickListener {


    ListView listview;
    TeacherAndStudentAdapter myBaseAdapter;
    TestBeanDao testBeanDao;
    TeacherDao teacherDao ;
    CardDao cardDao;
    StuDao stuDao ;
    CustomerBeanDao customerBeanDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        listview = (ListView) this.findViewById(R.id.listview);
        testBeanDao = new TestBeanDao();
        teacherDao = new TeacherDao();
        cardDao = new CardDao();
        stuDao = new StuDao();
        customerBeanDao=new CustomerBeanDao();

        customerBeanDao.createTable();

        cardDao.createTable();
        testBeanDao.createTable();



        teacherDao.createTable();
        stuDao.createTable();


        myBaseAdapter = new TeacherAndStudentAdapter();
        myBaseAdapter.setActivity(this);
        myBaseAdapter.setList(new ArrayList());
        DebugLog.isDebug=true;
        listview.setAdapter(myBaseAdapter);
        this.findViewById(R.id.queryByEnytity).setOnClickListener(this);
        this.findViewById(R.id.queryAll).setOnClickListener(this);
        this.findViewById(R.id.queryBuilder).setOnClickListener(this);
        this.findViewById(R.id.queryByID).setOnClickListener(this);
        this.findViewById(R.id.customerSQL).setOnClickListener(this);
        this.findViewById(R.id.layz_manytomany).setOnClickListener(this);
        this.findViewById(R.id.layz_manytoone).setOnClickListener(this);
        this.findViewById(R.id.layz_onetoone).setOnClickListener(this);
        this.findViewById(R.id.layz_onetoMany).setOnClickListener(this);

    }

    int id = 1;

    public void onClick(View v) {
        // TODO Auto-generated method stub


        switch (v.getId()) {
            case R.id.queryByID:
                myBaseAdapter.setList(null);

               Teacher listaaa = teacherDao.queryById(1);
                List list=new ArrayList();
                list.add(listaaa);
                myBaseAdapter.setList(list);

                break;

            case R.id.queryByEnytity:

                Teacher teacher = new Teacher();
                teacher.setSex("女");
                teacher.setIsGril(true);
                Student student=new Student();
                student.setStuName("name0");
                student.setAge(1);
                student.setId(1);
                teacher.setStudent(student);
                List<Teacher> listaaa1 = teacherDao.queryListByEntity(teacher);

                myBaseAdapter.setList(listaaa1);

                break;

            case R.id.queryAll:
             /*   Teacher  tt=   new  Teacher();
                tt.setId(2);
                tt.getStudent();*/
                myBaseAdapter.setList(new ArrayList());
                listaaa1 = teacherDao.queryAll();
                myBaseAdapter.setList(listaaa1);

                break;



            case R.id.queryBuilder:
                int lastid = teacherDao.queryLastInsertId();
                //  List list1= teacherDao.getQueryBuilder().callQuery().queryAll().where(Where.handle().orderBy("username").limit(10, lastid-10)).executeQueryList();
                //	List list1=teacherDao.executeQueryList("select * from  userTable where ");
                String table = teacherDao.getReflexEntity().getTableEntity().getTableName();
                String column = teacherDao.getReflexEntity().getTableIdEntity().getColumnName();
                List list1 = teacherDao.getQueryBuilder().callQuery().queryAll().where(Where.handle().and(column, "<=", lastid).and(column, ">", lastid - 10).orderBy("userid").desc()).executeQueryList();
                myBaseAdapter.setList(list1);

                break;
            case R.id.customerSQL:

                 list1 = teacherDao.executeQueryList("select * from  userTable  where  sex='女'");
                myBaseAdapter.setList(list1);

                break;

            case R.id.layz_manytomany:

                Teacher teacher1 = teacherDao.queryById(2);
              //   DebugLog.e(JSON.toJSONString(teacher1));
           //     if (true)return;
                List<Student> students=teacher1.holderStudents();
                myBaseAdapter.setList(students);

                break;

            case R.id.layz_manytoone:
                Student student1= stuDao.queryById(1);
                Teacher teacher2=   student1.getTeacher();
                List<Teacher> teachers=new ArrayList<>();
                teachers.add(teacher2);
                myBaseAdapter.setList(teachers);
                break;

            case R.id.layz_onetoone:
                  student1= stuDao.queryById(1);
                  DebugLog.e(student1.getStuName()+"  "+student1.getId());
                  teacher2=   student1.getTeacher();
                  teachers=new ArrayList<>();
                  teachers.add(teacher2);
                  myBaseAdapter.setList(teachers);
             break;

            case R.id.layz_onetoMany:
                DebugLog.e("-----------------------------------------------------");
                student = stuDao.queryById(1);

               // DebugLog.e(JSON.toJSONString(student));
                List<Teacher>  teachers1s=student.getTeachers();
                myBaseAdapter.setList(teachers1s);

                break;


            default:


                break;
        }


    }


}
