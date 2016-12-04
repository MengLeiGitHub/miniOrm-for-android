package com.test;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.miniorm.MiniOrm;
import com.miniorm.dao.builder.Where;
import com.miniorm.debug.DebugLog;
import com.test.test.CardDao;
import com.test.test.StuDao;
import com.test.test.Student;
import com.test.test.Teacher;
import com.test.test.TeacherDao;
import com.test.test.TestBean;
import com.test.test.TestBeanDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener {


    ListView listview;
    MyBaseAdapter myBaseAdapter;
    TestBeanDao testBeanDao;
    TeacherDao teacherDao ;
    CardDao cardDao;
    StuDao stuDao ;

    AlertDialog alertDialog;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        listview = (ListView) this.findViewById(R.id.listview);
        myBaseAdapter = new MyBaseAdapter();
        myBaseAdapter.setActivity(this);
        myBaseAdapter.setList(new ArrayList());
        DebugLog.isDebug=true;
        listview.setAdapter(myBaseAdapter);


        //	stuDao.createTable();
        //teacherDao.createTable();

        this.findViewById(R.id.save).setOnClickListener(this);
        this.findViewById(R.id.drop).setOnClickListener(this);
        this.findViewById(R.id.create).setOnClickListener(this);

        this.findViewById(R.id.delete).setOnClickListener(this);

        this.findViewById(R.id.select).setOnClickListener(this);

        this.findViewById(R.id.update).setOnClickListener(this);

        this.findViewById(R.id.queryBuilder).setOnClickListener(this);
        editText = new EditText(this);

        new AlertDialog.Builder(this).setTitle("请输入").setIcon(
                android.R.drawable.ic_dialog_info).setView(
                editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MiniOrm.init(getApplication(), 8, editText.getText().toString()+".db");
                MiniOrm.addUpdateTable(TeacherDao.class);
                testBeanDao = new TestBeanDao();
                teacherDao = new TeacherDao();
                cardDao = new CardDao();
                stuDao = new StuDao();
                cardDao.createTable();
                testBeanDao.createTable();
                teacherDao.createTable();


            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
        ;


    }

    int id = 1;

    public void onClick(View v) {
        // TODO Auto-generated method stub

        ArrayList<TestBean> testBeans = new ArrayList<TestBean>();
        for (int i = 0; i < 10; i++) {
            TestBean testBean = new TestBean();
            testBean.setId(i + 1);
            testBean.setUsername("asdad" + i);
            testBean.setContent("Content" + i);
            testBean.setCompany("Company" + i);
            testBean.setCompanyAddress("CompanyAddress" + i);
            testBean.setContactNickname("ContactNickname" + i);
            testBean.setGmtCreate(1000000 + i);
            testBean.setGroupId(123 + i);
            testBean.setType(1231 + i);

            testBean.setAvatar("123131321");
            testBeans.add(testBean);

        }

        final Student student = new Student();
        student.setAge(2);
        student.setStuName("王小明");
        //student.setId(-1);


        switch (v.getId()) {
            case R.id.save:
                int i = 0;
                ArrayList<Teacher> teachers = new ArrayList<>();
                for (int n = 1; n < 100; n++) {
                    Teacher teacher = new Teacher();
                    teacher.setSex(n % 2 == 0 ? "女" : "男");
                    teacher.setUserName(editText.getText().toString()+""+i);
                    teacher.setId(n);
                    teacher.setIsGril(n % 2 == 0);
                    teacher.setPwd("pwd" + n);
                    teachers.add(teacher);
                }
                teacherDao.saveOrUpdate(teachers);

                break;
            case R.id.delete:
                student.setId(2);
                student.setStuName("kkkk");
                stuDao.delete(student);
                teacherDao.deleteAll();
                teacherDao.executeQuery("select * from usertable",teacherDao.getQueryEntity(),teacherDao.getReflexEntity() );
                myBaseAdapter.setList(new ArrayList());
                break;
            case R.id.update:
                student.setId(2);
                student.setStuName("kkkk");

                stuDao.update(student);

                break;

            case R.id.select:

                Teacher teacher = new Teacher();
                teacher.setSex("女");
                teacher.setIsGril(true);
                List<Teacher> listaaa = teacherDao.queryListByEntity(teacher);
                teacherDao.queryByEntity(teacher);
                //List<Teacher> listaaa=teacherDao.getQueryBuilder().callQuery().queryAll().where(Where.handle().eq("sex","女")).executeQueryList();
                Log.e("tag", listaaa.size() + "");
                myBaseAdapter.setList(listaaa);
                int id = teacherDao.queryLastInsertId();
                Log.e("tag", "id=" + id);


                break;
            case R.id.drop:
                i = stuDao.drop();
                teacherDao.drop();
                myBaseAdapter.setList(new ArrayList());

                if (i != 0) Toast.makeText(this, "表已经删除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.create:
                // 	i=stuDao.createTable();
                i = teacherDao.createTable();
                if (i != 0) Toast.makeText(this, "表已经创建", Toast.LENGTH_SHORT).show();
                break;

            case R.id.queryBuilder:
                int lastid = teacherDao.queryLastInsertId();
                //  List list1= teacherDao.getQueryBuilder().callQuery().queryAll().where(Where.handle().orderBy("username").limit(10, lastid-10)).executeQueryList();
                //	List list1=teacherDao.executeQueryList("select * from  userTable where ");
                String table = teacherDao.getReflexEntity().getTableEntity().getTableName();
                String column = teacherDao.getReflexEntity().getTableIdEntity().getColumnName();
                List list1 = teacherDao.getQueryBuilder().callQuery().queryAll().where(Where.handle().and(column, "<=", lastid).and(column, ">", lastid - 10).desc()).executeQueryList();
                myBaseAdapter.setList(list1);

                break;
            default:


                break;
        }


    }


}
