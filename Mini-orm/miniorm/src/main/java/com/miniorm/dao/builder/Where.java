package com.miniorm.dao.builder;

import java.util.LinkedList;


public class Where {
	
	private String  tableName;
	
	private LinkedList<String>  list;
	
	
	
	public  Where(){
		list=new LinkedList<String>();
	}
	
	public void   and(String   columnName,String op,Object obj){
		StringBuilder  sb=new StringBuilder();
		sb.append(" ");
		sb.append(columnName);
		sb.append("=");
		if(obj instanceof String){
			sb.append("'");
			sb.append(obj.toString());
			sb.append("'");
			
		}else{
 			sb.append(obj.toString());
 		}
		
		list.add(sb.toString());
 	}
	
	public void   or(String   columnName,String op,Object obj){
		StringBuilder  sb=new StringBuilder();
		sb.append(" ");
		sb.append(columnName);
		sb.append("=");
		if(obj instanceof String){
			sb.append("'");
			sb.append(obj.toString());
			sb.append("'");
			
		}else{
 			sb.append(obj.toString());
 		}
		
		list.add(sb.toString());
 	}
}
