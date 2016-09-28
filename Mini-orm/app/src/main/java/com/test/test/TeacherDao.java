package com.test.test;


import com.miniorm.android.androidBaseDao;

public class TeacherDao  extends androidBaseDao<Teacher> {

	@Override
	public Teacher getQueryEntity() {
		// TODO Auto-generated method stub
		Teacher teacher=new Teacher();
		teacher.setStudent(new Student());

		return teacher;
	}

	 

}
