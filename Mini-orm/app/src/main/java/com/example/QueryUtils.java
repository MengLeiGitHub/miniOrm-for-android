package com.example;

import com.example.bean.CourseDao;
import com.example.bean.SchoolClass;
import com.example.bean.SchoolClassDao;
import com.example.bean.SchoolClassTeacher;
import com.example.bean.SchoolClassTeacherDao;
import com.example.bean.Teacher;
import com.example.bean.TeacherDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017-04-09.
 */

public class QueryUtils {

    ListData<Teacher>  queryAllTeacher;

    public void setQueryAllTeacher(ListData<Teacher> queryAllTeacher) {
        this.queryAllTeacher = queryAllTeacher;
    }

    public void   queryTeacher(){

    }

    public List<SchoolClass> queryAllClass(SchoolClassDao schoolClassDao) {
        return     schoolClassDao.queryAll();
    }

    public List<Teacher> queryAllTeacher(TeacherDao teacherDao) {
        List<Teacher> list=teacherDao.queryAll();
        return list;
    }

    //查询老师是否在班里代课
    public boolean queryTeacherInClass(Teacher teacher, SchoolClass currentClass, SchoolClassTeacherDao schoolClassTeacherDao) {
        SchoolClassTeacher schoolClassTeacher=new SchoolClassTeacher();
        schoolClassTeacher.setTeacher(teacher);
        schoolClassTeacher.setSchoolClass(currentClass);
        return     !(schoolClassTeacherDao.queryByEntity(schoolClassTeacher)==null);
    }
    public List<Teacher> queryTeachersInClass(SchoolClass currentClass, SchoolClassTeacherDao schoolClassTeacherDao) {
        List list=currentClass.getTeachers();
       return list==null?new ArrayList<Teacher>():list;
    }

    public interface ListData<T> {
        void   setData(List<T> list);
    }

}
