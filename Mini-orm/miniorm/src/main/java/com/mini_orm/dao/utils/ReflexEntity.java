package com.mini_orm.dao.utils;



import com.mini_orm.entity.TableColumnEntity;
import com.mini_orm.entity.TableEntity;
import com.mini_orm.entity.TableIdEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ReflexEntity {
	private String tableName;
	
	/**
 	 */
	private Set<String> keyset;
	
	/**
 	 */
	private HashMap<String, Object >   keyValue;
	
	
	 
	
 	private HashMap<String, TableColumnEntity>    tableColumnMap;
	
	private TableEntity tableEntity;
	
	private TableIdEntity tableIdEntity;
	
	
	private String[]  condition;
	
	
 	
	public ReflexEntity(){
		keyset=new HashSet<String>();
		keyValue=new HashMap<String, Object>();
		tableColumnMap=new HashMap<String, TableColumnEntity>();
	}
	
	public void  addKeyValue(String key,Object obj){
		keyset.add(key);
		keyValue.put(key, obj);
	}
	
	public  void   addKey(String key){
		keyset.add(key);
 	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Set<String> getKeyset() {
		return keyset;
	}
	public void setKeyset(Set<String> keyset) {
		this.keyset = keyset;
	}
	public HashMap<String, Object> getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(HashMap<String, Object> keyValue) {
		this.keyValue = keyValue;
	}

	public TableEntity getTableEntity() {
		return tableEntity;
	}

	public void setTableEntity(TableEntity tableEntity) {
		this.tableEntity = tableEntity;
	}

	public TableIdEntity getTableIdEntity() {
		return tableIdEntity;
	}

	public void setTableIdEntity(TableIdEntity tableIdEntity) {
		this.tableIdEntity = tableIdEntity;
	}

	public HashMap<String, TableColumnEntity> getTableColumnMap() {
		return tableColumnMap;
	}

	public String[] getCondition() {
		return condition;
	}

	public void setCondition(String[] condition) {
		this.condition = condition;
	}
	
 
}
