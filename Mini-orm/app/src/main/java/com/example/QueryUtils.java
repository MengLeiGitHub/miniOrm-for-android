package com.example;

import com.example.bean.SchoolClass;
import com.example.bean.SchoolClassTeacher;
import com.example.bean.Teacher;
import com.miniorm.MiniOrm;
import com.miniorm.MiniOrmUtils;

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

    public List<SchoolClass> queryAllClass() {

        return    MiniOrmUtils.getInstance().getDao(SchoolClass.class).queryAll();
    }

    public List<Teacher> queryAllTeacher() {
        return MiniOrmUtils.getInstance().getDao(Teacher.class).queryAll();
    }

    //查询老师是否在班里代课
    public boolean queryTeacherInClass(Teacher teacher, SchoolClass currentClass) {
        SchoolClassTeacher schoolClassTeacher=new SchoolClassTeacher();
        schoolClassTeacher.setTeacher(teacher);
        schoolClassTeacher.setSchoolClass(currentClass);
        return     !( MiniOrmUtils.getInstance().getDao(SchoolClassTeacher.class).queryByEntity(schoolClassTeacher)==null);
    }
    public List<Teacher> queryTeachersInClass(SchoolClass currentClass) {
        List list=currentClass.getTeachers();
       return list==null?new ArrayList<Teacher>():list;
    }

    public interface ListData<T> {
        void   setData(List<T> list);
    }

}
