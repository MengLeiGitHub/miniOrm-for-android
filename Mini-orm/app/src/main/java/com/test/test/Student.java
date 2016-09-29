package com.test.test;


import com.miniorm.android.ColumnType;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;

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


	@Override
	public String toString() {
		return "age="+age+"  stuname="+stuName+" id="+id;
	}
}
