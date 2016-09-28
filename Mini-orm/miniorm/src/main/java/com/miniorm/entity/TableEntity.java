package com.miniorm.entity;

public class TableEntity {
	private String tableName;
	private  Class clas;
	public TableEntity(String tablename,Class cls){
		this.clas=cls;
		this.tableName=tablename;
	}
	
	

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}



	public Class getClas() {
		return clas;
	}



	public void setClas(Class clas) {
		this.clas = clas;
	}
	

}
