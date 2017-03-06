package com.test.test;


import com.miniorm.android.androidBaseDao;
import com.miniorm.android.androidBaseDao2;

public class TeacherDao  extends androidBaseDao2<Teacher> {

	@Override
	public Teacher getQueryEntity() {
		// TODO Auto-generated method stub
		Teacher teacher=new Teacher();
		return teacher;
	}

	 

}
