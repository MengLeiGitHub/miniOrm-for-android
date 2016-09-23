package com.mini_orm.test;


import com.mini_orm.android.ColumnType;
import com.mini_orm.annotation.Table;
import com.mini_orm.annotation.TableColumn;
import com.mini_orm.annotation.TableID;
import com.mini_orm.enumtype.Parmary;

@Table(name="student")
public class Student {
	
	@TableID(name="sid",isPrimaryKey=true,defaultVal=0,type= Parmary.AutoIncrement,columnType= ColumnType.INTEGER)
	private int id ;
	
	@TableColumn(name="stuname",columnType=ColumnType.TEXT)
	private String stuName;
	
	
	@TableColumn(name="age",columnType=ColumnType.INTEGER)
	private int age;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getStuName() {
		return stuName;
	}


	public void setStuName(String stuName) {
		this.stuName = stuName;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}
	
	
	

}
