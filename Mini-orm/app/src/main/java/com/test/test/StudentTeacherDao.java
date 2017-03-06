package com.test.test;

import com.miniorm.android.androidBaseDao2;

/**
 * Created by admin on 2017-03-01.
 */

public class StudentTeacherDao extends androidBaseDao2<StudentTeacher>{

    @Override
    public StudentTeacher getQueryEntity() {

        return new StudentTeacher();
    }

}
