package com.test.test;


import com.miniorm.android.androidBaseDao;
import com.miniorm.android.androidBaseDao2;

public class StuDao extends androidBaseDao2<Student> {

	@Override
	public Student getQueryEntity() {
		// TODO Auto-generated method stub
		return new Student();
	}

}
