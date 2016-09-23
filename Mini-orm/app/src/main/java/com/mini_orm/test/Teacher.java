package com.mini_orm.test;

import com.mini_orm.android.ColumnType;
import com.mini_orm.annotation.Table;
import com.mini_orm.annotation.TableColumn;
import com.mini_orm.annotation.TableID;
import com.mini_orm.enumtype.Parmary;

@Table(name="userTable")
public class Teacher {

	@TableColumn(name="username",columnType= ColumnType.TEXT)
	private  String userName;

	@TableColumn(name="pwd",columnType=ColumnType.TEXT)
	private  String pwd;
	

	@TableID(isPrimaryKey=true,name="userid",defaultVal=-1,type= Parmary.AutoIncrement,columnType=ColumnType.INTEGER)
	private int id;
	
	@TableColumn(name="sid",isForeignkey=true,columnType=ColumnType.TEXT)
	private Student student;
	
 	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	
	
}
