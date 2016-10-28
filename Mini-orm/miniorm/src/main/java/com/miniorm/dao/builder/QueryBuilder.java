package com.miniorm.dao.builder;



import android.util.Log;

import com.miniorm.android.parseType.ParseTypeFactory;
import com.miniorm.annotation.TableID;
import com.miniorm.dao.database.QueryInterface;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.annotation.Table;
import com.miniorm.dao.BaseDao;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder<T> {
	BaseDao<T> baseDao=null;
	QueryInterface  queryInterface;
	
	ReflexEntity  reflexEntity=null;
	

	
	StringBuilder  sql=new StringBuilder();

	
	private Query query;

	T    t;
	
	public    QueryBuilder(BaseDao<T> baseDao){
		this.baseDao=baseDao;
		this.t=baseDao.getQueryEntity();
		reflexEntity=ReflexCache.getReflexEntity(t.getClass().getName());
		query =new Query();

	}

	public  Query  callQuery(){
		return query;
	}
	

	
	
	public class   Query{
		
	private 	final String  Placeholder=" /8/ ";


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
			
			sql.append("  from  ");
				
		 	sql.append(reflexEntity.getTableEntity().getTableName());
			
			return this;
		}
		
		public  Query  queryAll(){

   				 sql.append(baseDao.getQueryInterface().queryAll(reflexEntity));
			     int index=sql.indexOf(";");
			     if(index>=0)
			     sql.deleteCharAt(index);
 			return this;
		}

 		public  T      excute(){
			String sqls=sql.toString().replaceAll(Placeholder, "")+";";
			return 	baseDao.executeQuery(sqls,t,reflexEntity);
		}
		
		public  List<T>      executeQueryList(){

			String sqls=sql.toString().replaceAll(Placeholder, "")+";";
			sql.delete(0,sqls.length());
			return baseDao.executeQueryList(sqls);
		}


		public Query where(Where where) {
			sql.append(where.sql());
			return this;
		}
	}
	
}
