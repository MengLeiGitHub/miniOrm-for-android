package com.miniorm.dao.builder;

public class Where {

	private StringBuilder  sql=null;

	private 	final String  where ="  where  ";

	private  final String  Placeholder=" /8/ ";

	public  static  Where  handle(){

		return  new Where();
	}

	public Where(){
		sql=new StringBuilder();
	}



	public Where   and(String   columnName,String op,Object obj){

		if(sql.indexOf(where)<0){
			where();
		}
		if(sql.toString().endsWith(Placeholder)){
			and();
		}
		sql.append(" ");
		sql.append(columnName);
		sql.append(op);
		if(obj instanceof String){
			sql.append("'");
			sql.append(obj.toString());
			sql.append("'");

		}else{
			sql.append(obj.toString());
		}
		sql.append(Placeholder);

		return this;
	}


	public  Where  and(){
		sql.append(" and ");
		return this;
	}

	private Where where(){
		sql.append(where);
		return this;
	}

	public	Where  eq(String key,String val){
		if(sql.indexOf(where)<0){
			where();
		}
		if(sql.toString().endsWith(Placeholder)){
			and();
		}
		sql.append(key);
		sql.append("=");
		sql.append("'"+val+"'");
		sql.append(Placeholder);

		return this;

	}

	public	Where  eq(String key,int val){
		if(sql.indexOf(where)<0){
			where();
		}
		if(sql.toString().endsWith(Placeholder)){
			and();
		}
		sql.append(key);
		sql.append("=");
		sql.append(val);
		sql.append(Placeholder);

		return this;

	}
	public	Where  eq(String key,boolean  val){
		if(sql.indexOf(where)<0){
			where();
		}
		if(sql.toString().endsWith(Placeholder)){
			and();
		}
		sql.append(key);
		sql.append("=");
		sql.append(val);
		sql.append(Placeholder);

		return this;

	}

	public  Where   orderBy(String column){
		sql.append("  ORDER BY  ");
		sql.append(column);
		return  this;
	}

	public  Where   groupBy(String column){
		sql.append("  ORDER BY  ");
		sql.append(column);

		return  this;
	}
	public  Where   desc( ){
		sql.append("  DESC  ");

		return  this;
	}
	public  Where   asc( ){
		sql.append("  ASC  ");

		return  this;
	}
	public  Where  or(){

		sql.append("  or  ");

		return this;
	}


	public  Where  limit(int start,int  end){

		sql.append("   LIMIT  ");

		sql.append(end);
		sql.append("   OFFSET  ");

		sql.append(start);

		return this;
	}
	public  Where  limit(int start){

		sql.append("   LIMIT  ");

		sql.append(start);

		return this;
	}
	public Where  and(String sql){
		if(!sql.toLowerCase().contains("where")){
			where();
		}
		this.sql.append(sql);
		return this;
	}
	public  String  sql(){
		String sqls=sql.toString().replaceAll(Placeholder," ");
		sql.delete(0,sql.length()-1);
		return  sqls;
	}



}
