package com.mini_orm.entity;


import com.mini_orm.enumtype.Parmary;

public class TableIdEntity {

	private String fieldname;
 	
	private  int  defaultVal ;
	
	private  boolean  isPrimaryKey ;
	
	private  String   columnType;
	
	private  String   columnName;
	private  Object   columnVal;

	private Parmary keytype;
	

	public String getName() {
		return fieldname;
	}

	public void setFieldName(String name) {
		this.fieldname = name;
	}

	public int getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(int defaultVal) {
		this.defaultVal = defaultVal;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public Parmary getKeytype() {
		return keytype;
	}

	public void setKeytype(Parmary keytype) {
		this.keytype = keytype;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Object getColumnVal() {
		return columnVal;
	}

	public void setColumnVal(Object columnVal) {
		this.columnVal = columnVal;
	}

	  
	
	
}
