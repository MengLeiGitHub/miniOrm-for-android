package com.synb;


import com.synb.bean.SchoolClass;

import com.synb.bean.SchoolClassTeacher;

import com.synb.bean.Teacher;

import com.miniorm.dao.BaseDao;

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

    public List<SchoolClass> queryAllClass(BaseDao<SchoolClass> schoolClassDao) {
        return     schoolClassDao.queryAll();
    }

    public List<Teacher> queryAllTeacher(BaseDao< Teacher>  teacherDao) {
        List<Teacher> list=teacherDao.queryAll();
        return list;
    }

    //查询老师是否在班里代课
    public boolean queryTeacherInClass(Teacher teacher, SchoolClass currentClass, BaseDao< SchoolClassTeacher>  schoolClassTeacherDao) {
        SchoolClassTeacher schoolClassTeacher=new SchoolClassTeacher();
        schoolClassTeacher.setTeacher(teacher);
        schoolClassTeacher.setSchoolClass(currentClass);
        return     !(schoolClassTeacherDao.queryByEntity(schoolClassTeacher)==null);
    }
    public List<Teacher> queryTeachersInClass(SchoolClass currentClass, BaseDao< SchoolClassTeacher>  schoolClassTeacherDao) {
        List list=currentClass.getTeachers();
       return list==null?new ArrayList<Teacher>():list;
    }

    public interface ListData<T> {
        void   setData(List<T> list);
    }

}
