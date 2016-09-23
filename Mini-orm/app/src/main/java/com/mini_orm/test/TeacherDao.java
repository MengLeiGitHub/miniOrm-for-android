package com.mini_orm.test;


import com.mini_orm.android.androidBaseDao;

public class TeacherDao  extends androidBaseDao<Teacher> {

	@Override
	public Teacher getQueryEntity() {
		// TODO Auto-generated method stub
		return new Teacher();
	}

	 

}
