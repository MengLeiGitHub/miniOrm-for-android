package com.miniorm.dao.builder;



import com.miniorm.android.parseType.ParseTypeFactory;
import com.miniorm.annotation.TableID;
import com.miniorm.dao.utils.EntityParse;
import com.miniorm.annotation.Table;
import com.miniorm.dao.BaseDao;
import com.miniorm.dao.utils.ReflexEntity;

import java.util.List;

public class QueryBuilder<T> {
	BaseDao<T> baseDao=null;
	
	EntityParse<T> entityParse=null;
	
	Table table;
	
	TableID[]  tableids=null;
	
	StringBuilder  sql=new StringBuilder();

	
	private Query query;

	T    t;
	
	public  QueryBuilder(Class clas){
		this.baseDao= ParseTypeFactory.getEntityParse(clas.getSimpleName());
		t=baseDao.getQueryEntity();
		entityParse=new EntityParse<T>(t);
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
		
		
		
		public  T    queryOne(String sql){

			return  excute(sql);
		}
		
		public List<T>  list(){
			System.out.println(sql.toString());
			return null;
		}
		public  T      excute(){
			String sqls=sql.toString().replaceAll(Placeholder, "")+";";
			ReflexEntity f=new ReflexEntity();
		    baseDao.executeQuery(sqls,t,f);
			
			return null;
		}
		
		public  T      excute(String  sql){
 			
			System.out.println(sql);
			
			return baseDao.executeQuery(sql, t,entityParse.getFieldValueFromT(t));
		}
		
		
		
		
	}
	
}
