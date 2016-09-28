package com.miniorm.dao.builder;



import com.miniorm.annotation.TableID;
import com.miniorm.dao.utils.EntityParse;
import com.miniorm.annotation.Table;
import com.miniorm.dao.BaseDao;

import java.util.List;

public class QueryBuilder<T> {
	BaseDao<T> baseDao=null;
	
	EntityParse<T> entityParse=null;
	
	Table table;
	
	TableID[]  tableids=null;
	
	StringBuilder  sql=new StringBuilder();

	
	private Query query;
	
	
	public  QueryBuilder(BaseDao<T> baseDao){
		this.baseDao=baseDao;
		entityParse=new EntityParse<T>(baseDao.getQueryEntity());
	}
	
	
	public Query  CallQueryWorker(){
		query =new Query();
		return query;
	}
	
	
	
	public class   Query{
		
		final String  where =" where ";
		final String  Placeholder=" /8/ ";
		
		public    Query(){
 
			table=entityParse.getTable();
 			tableids=entityParse.getTableIDs();
 			sql.append("select  ");
 			
 			
		}
		/**
		 * setter/getter
		 * @param val
		 */
	  
		public  Query  select(String ...val){
			for(String me:val){
			    sql.append(me);
			    sql.append(" ,");
 			}
			
		 
			if(sql.toString().endsWith(",")){
				sql.deleteCharAt(sql.length()-1);
			}
			
			sql.append("  from ");
				
		 	sql.append(entityParse.getTable().name());
			
			return this;
		}
		
		public  Query  select(){
			
   				sql.append("* from ");
  				
  				sql.append(entityParse.getTable().name());
			 
			return this;
		}
		
		
		public  Query  where(){
			sql.append(where);
			return this;
		}
		public  Query  and(){
			sql.append(" and ");
			return this;
		}
		
		
		public	Query  eq(String key,String val){
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
		public	Query  eq(String key,char val){
			
			sql.append(key);
			sql.append("=");
			sql.append("'"+val+"'");
 			
			
			return this;
			
		}
		public	Query  eq(String key,int val){
			
			sql.append(key);
			sql.append("=");
			sql.append(val);
 			
			
			return this;
			
		}
		public	Query  eq(String key,boolean  val){
			
			sql.append(key);
			sql.append("=");
			sql.append(val);
 			
			
			return this;
			
		}
		public  Query  or(){
			
			sql.append("  or  ");
			
 			return this;
		}
		
		
		
		
		
		public List<T>  list(){
			System.out.println(sql.toString());
			return null;
		}
		public  T      excute(){
			String sqls=sql.toString().replaceAll(Placeholder, "")+";";
			
			System.out.println(sqls);
			
			return null;
		}
		
		public  T      excute(String  sql){
 			
			System.out.println(sql);
			
			return baseDao.executeQuery(sql, baseDao.getQueryEntity(),null);
		}
		
		
		
		
	}
	
}
