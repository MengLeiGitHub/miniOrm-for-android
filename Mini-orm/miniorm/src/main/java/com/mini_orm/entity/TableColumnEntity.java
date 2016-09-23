package com.mini_orm.entity;

import java.lang.reflect.Method;

public class TableColumnEntity {

	private String columnName;

	private boolean isPrimaryKey;

	private boolean isForeignkey;

	private Method method;
	
	private Object columnVal;
	
	private Object fieldObject;//
	
	private String  columnType;
	

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String name) {
		this.columnName = name;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public boolean isForeignkey() {
		return isForeignkey;
	}

	public void setForeignkey(boolean isForeignkey) {
		this.isForeignkey = isForeignkey;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	 

	public Object getColumnVal() {
		return columnVal;
	}

	public void setColumnVal(Object columnVal) {
		this.columnVal = columnVal;
	}

	public Object getFieldObject() {
		return fieldObject;
	}

	public void setFieldObject(Object fieldObject) {
		this.fieldObject = fieldObject;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

}
