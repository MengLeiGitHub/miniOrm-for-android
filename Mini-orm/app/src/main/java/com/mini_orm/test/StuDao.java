package com.mini_orm.test;


import com.mini_orm.android.androidBaseDao;

public class StuDao extends androidBaseDao<Student> {

	@Override
	public Student getQueryEntity() {
		// TODO Auto-generated method stub
		return new Student();
	}

}
