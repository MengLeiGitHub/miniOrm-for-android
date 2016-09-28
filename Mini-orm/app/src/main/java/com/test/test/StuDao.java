package com.test.test;


import com.miniorm.android.androidBaseDao;

public class StuDao extends androidBaseDao<Student> {

	@Override
	public Student getQueryEntity() {
		// TODO Auto-generated method stub
		return new Student();
	}

}
