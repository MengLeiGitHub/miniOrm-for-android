package com.test.test;


import com.miniorm.android.ColumnType;
import com.miniorm.annotation.ManyToMany;
import com.miniorm.annotation.ManyToOne;
import com.miniorm.annotation.OneToMany;
import com.miniorm.annotation.OneToOne;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;

import java.util.List;

@Table(name="student")
public class Student {
	
	@TableID(name="sid",isPrimaryKey=true,defaultVal=0,type= Parmary.AutoIncrement,columnType= ColumnType.INTEGER)
	private int id ;
	
	@TableColumn(name="stuname",columnType=ColumnType.TEXT)
	private String stuName;
	
	
	@TableColumn(name="age",columnType=ColumnType.INTEGER)
	private int age;

	//@TableColumn(name="tid",columnType=ColumnType.INTEGER,isForeignkey = true,HierarchicalQueries=false)
	private Teacher teacher;

	private List<Teacher> teachers;


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

	@OneToOne
	public Teacher getTeacher() {
		return teacher;
	}

	@ManyToOne
	@OneToOne
	public Teacher holderTeacher() {
		return teacher;
	}


	@ManyToMany(bridgingTable=StudentTeacher.class)
	public List<Teacher> holderTeachers() {
		return null;
	}
	@OneToMany
	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "age="+age+"  stuname="+stuName+" id="+id;
	}
}
