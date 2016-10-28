package com.test.test;

import com.miniorm.android.ColumnType;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;

@Table(name="userTable")
public class Teacher {

	@TableColumn(name="username",columnType= ColumnType.TEXT)
	private  String userName;

	@TableColumn(name="pwd",columnType=ColumnType.TEXT)
	private  String pwd;
	

	@TableID(isPrimaryKey=true,name="userid",defaultVal=0,type= Parmary.AutoIncrement,columnType=ColumnType.INTEGER)
	private int id;
	
	@TableColumn(name="sid",isForeignkey=true,columnType=ColumnType.INTEGER,HierarchicalQueries = true)
	private Student student;

	@TableColumn(name="sex",columnType=ColumnType.VARCHAR)
	private  String sex;
	@TableColumn(name="shengao",columnType=ColumnType.INTEGER)
	private  int shengao;


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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getShengao() {
		return shengao;
	}

	public void setShengao(int shengao) {
		this.shengao = shengao;
	}

}
