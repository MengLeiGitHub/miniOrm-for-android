package com.example;

import android.Manifest;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.adapter.LessionAdpter;
import com.example.adapter.SchoolClassAdpter;
import com.example.adapter.StudentAdpter;
import com.example.adapter.TecherAdpter;
import com.example.bean.Course;
import com.example.bean.NeiBuLeiTest;
import com.example.bean.SchoolClass;
import com.example.bean.SchoolClassTeacher;
import com.example.bean.Student;
import com.example.bean.Teacher;
import com.example.bean.TestBean;
import com.miniorm.MiniOrm;
import com.miniorm.MiniOrmUtils;
import com.miniorm.dao.BaseDao;
import com.miniorm.dao.utils.ResultType;
import com.test.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2017-04-05.
 */

public class ManActivity extends Activity {

    BaseDao<Course> courseDao;//课程
    BaseDao< SchoolClass> schoolClassDao;//班级
    BaseDao< SchoolClassTeacher> schoolClassTeacherDao;//班级老师表
    BaseDao< Student> studentDao;//学生
    BaseDao< Teacher> teacherDao;//老师

    Spinner courseSp,classSp;
    GridView teacherlistgridview,TeachingGridview,StudentGridview,teacherlessionview;

    SchoolClass currentClass;//当前选择的班级
    QueryUtils queryUtils;
    TecherAdpter  teachingadapter;
    StudentAdpter studentAdpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenAdapter.match(this,375);
        setContentView(R.layout.activity_main);
        queryUtils = new QueryUtils();
        String[] permis= new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        boolean isp=PermissionUtil.hasPermissons(this,permis);
        if (!isp)
        PermissionUtil.requestPerssions(this,123,permis);
        else {
            initOrm();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long time=System.currentTimeMillis();
                    List<TestBean> testBeans=new ArrayList<>();
                    for (int i=0;i<1000000;i++){
                        TestBean testBean=new TestBean();
                        testBean.setName("name="+i);
                        testBean.setSid(i+1);
                        testBeans.add(testBean);
                    }
                    MiniOrmUtils.getInstance().getDao(TestBean.class).saveOrUpdate(testBeans);
                    Log.e("time","使用时间："+Long.toString(System.currentTimeMillis()-time));
                }
            })/*.start()*/;

        }
        LinkedList linkedList=new LinkedList();
        Iterator iterator= linkedList.iterator();
        while (iterator.hasNext()){
            iterator.next();
        }


    }

    private void initOrm() {
        long time1=System.currentTimeMillis();
        MiniOrmUtils.getInstance().init(getApplication(),"test.db",1,"caosimafdlj");
/*        MiniOrm.init(ManActivity.this.getApplication(),1,"test.db");
        MiniOrm.useSDCard(true,"miniorm");*/
        MiniOrmUtils.getInstance().createTables();
        init();
        Log.e("tag  CreateTable",(time1-System.currentTimeMillis())+"");
        time1=System.currentTimeMillis();
        initTableData();
        Log.e("tag  initTableData",(time1-System.currentTimeMillis())+"");
        initWidget();
        NeiBuLeiTest.HAHA haha=new NeiBuLeiTest.HAHA();
        haha.setcName("卧槽");
        haha.setId(123);
        MiniOrmUtils.getInstance().getDao(NeiBuLeiTest.HAHA.class).save(haha);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, new PermissionUtil.OnRequestPermissionsResultCallbacks() {
            @Override
            public void onPermissionsGranted(int requestCode, List<String> perms, boolean isAllGranted) {
                if (isAllGranted)
                initOrm();
            }

            @Override
            public void onPermissionsDenied(int requestCode, List<String> perms, boolean isAllDenied) {

            }
        });



    }

    private void initWidget() {


           /*查询所有的班级*/
        final List  classList=queryUtils.queryAllClass(schoolClassDao);
        SchoolClassAdpter schoolClassAdpter=new SchoolClassAdpter(this, classList);
        classSp=findViewByIds(R.id.classSp);
        classSp.setAdapter(schoolClassAdpter);
        classSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentClass= (SchoolClass) classList.get(position);
                updateView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        teacherlistgridview = findViewByIds(R.id.techerlist);

        StudentGridview=findViewByIds(R.id.StudentGridview);

        /*查询所有的老师*/
        List list = queryUtils.queryAllTeacher(teacherDao);
        TecherAdpter techerAdpter = new TecherAdpter(this, list);
        techerAdpter.setOnItemClick(new OnItemClick<Teacher>() {
            @Override
            public void ItemClick(final Teacher teacher) {
               boolean  isExist= queryUtils.queryTeacherInClass(teacher,currentClass,schoolClassTeacherDao);

                if(!isExist){
                    SchoolClassTeacher schoolClassTeacher = new SchoolClassTeacher();
                    schoolClassTeacher.setSchoolClass(currentClass);
                    schoolClassTeacher.setTeacher(teacher);
                    int result=schoolClassTeacherDao.saveOrUpdate(schoolClassTeacher);
                    if(result== ResultType.SUCCESS){

                    }
                }
                else {
                    SchoolClassTeacher schoolClassTeacher=new SchoolClassTeacher();
                    schoolClassTeacher.setTeacher(teacher);
                    schoolClassTeacher.setSchoolClass(currentClass);
                    int result= schoolClassTeacherDao.delete(schoolClassTeacher);
                    if(result== ResultType.SUCCESS){

                    }
                }
                List<Teacher>  teacherList=queryUtils.queryTeachersInClass(currentClass,schoolClassTeacherDao);
                teachingadapter.setList(teacherList);
            }
        });
        teacherlistgridview.setAdapter(techerAdpter);



        courseSp = findViewByIds(R.id.courseSp);
        final List<Course> cursorList=courseDao.queryAll();
        courseSp.setAdapter(new LessionAdpter(this,cursorList));
        final TecherAdpter lession=new TecherAdpter(this,new ArrayList<Teacher>());
        courseSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   Course course=cursorList.get(position);
                    lession.setList(course.getTeachers());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        teacherlessionview=findViewByIds(R.id.teacherlessionview);

        teacherlessionview.setAdapter(lession);


    }


    private void   updateView(){

        /*查询某班级下的所有教学的老师*/
        TeachingGridview=findViewByIds(R.id.TeachingGridview);
        List<Teacher>  teacherList=queryUtils.queryTeachersInClass(currentClass,schoolClassTeacherDao);
        TeachingGridview.setAdapter(teachingadapter=new TecherAdpter(this,teacherList));
        teachingadapter.setOnItemClick(new OnItemClick<Teacher>() {
            @Override
            public void ItemClick(Teacher teacher) {
                String teachingName=teacher.getCourse().getcName();
                ToolUtils.showToast(ManActivity.this,teacher.getName()+" :" +teachingName);
            }
        });

        /*查询某班级的所有学生*/

        List<Student>  studentList=currentClass.getStudentList();
        studentAdpter=new StudentAdpter(this,studentList);
        StudentGridview.setAdapter(studentAdpter);
        studentAdpter.setOnItemClick(new OnItemClick<Student>() {
            @Override
            public void ItemClick(Student student) {

            }
        });
    }










    void init() {
        courseDao = MiniOrmUtils.getInstance().getDao(Course.class);
        schoolClassDao = MiniOrmUtils.getInstance().getDao(SchoolClass.class);//班级
        schoolClassTeacherDao = MiniOrmUtils.getInstance().getDao(SchoolClassTeacher.class);;//班级老师表
        studentDao = MiniOrmUtils.getInstance().getDao(Student.class);;//学生
        teacherDao = MiniOrmUtils.getInstance().getDao(Teacher.class);//老师
    }

    private void initTableData() {
        List<SchoolClass> schoolClasses = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            SchoolClass schoolClass = new SchoolClass();
            schoolClass.setsClassName("一年级" + i + "班");
            schoolClasses.add(schoolClass);

            schoolClass.setSid(i);
        }
        schoolClassDao.saveOrUpdate(schoolClasses);

        //初始化时 ，将 学生和班级直接 关联起来
        int[] class_students = {R.array.class1, R.array.class2, R.array.class3};
        List<Student> students = new ArrayList<>();
        for (SchoolClass schoolClass : schoolClasses) {
            int resouresid = (schoolClass.getSid() - 1);
            String[] names = getResources().getStringArray(class_students[resouresid]);
            for (int i = 1; i < names.length; i++) {
                 Student student = new Student();
                student.setSchoolClass(schoolClass);
                student.setName(names[i - 1]);
                students.add(student);
            }
        }
        studentDao.saveOrUpdate(students);


        String[] cours = getResources().getStringArray(R.array.courses);
        List<Course> courses = new ArrayList<>();
        for (int i = 1; i <= cours.length; i++) {
            Course course = new Course();
            course.setcName(cours[i - 1]);
            course.setId(i);
            courses.add(course);
        }
        courseDao.saveOrUpdate(courses);

        List<Teacher> teacherList = new ArrayList<>();
        String[] teachers = getResources().getStringArray(R.array.techers);

        for (int i = 0; i < teachers.length; i++) {
            Teacher teacher = new Teacher();
            teacher.setName(teachers[i]);
            teacher.setTid(i + 1);
            teacher.setCourse(courses.get(i%courses.size()));
            teacherList.add(teacher);
        }
        teacherDao.saveOrUpdate(teacherList);

    }

    public <T extends View> T findViewByIds(int id) {
        //return super.findViewById(id);
        return (T) this.getWindow().getDecorView().findViewById(id);

    }


}
