package com.miniorm.query.sql;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.miniorm.android.KeyWork;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.dao.utils.StringUtils;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.query.analysis.BaseSqlAnalysis;
import com.miniorm.query.analysis.general.GeneralQueryAllSqlAnalysis;
import com.miniorm.query.analysis.general.GeneralQueryByOtherSqlAnalysis;
import com.miniorm.query.analysis.hierarchical.HierarchicalQueryAllAnalysis;
import com.miniorm.query.analysis.hierarchical.HierarchicalQueryByOtherAnalysis;

public class QueryAllCreater<T> extends SQLCreater<T>{
	BaseSqlAnalysis<T>  baseSqlAnalysis;


	public QueryAllCreater(ReflexEntity reflexEntity, Class<T> t) {
		super(reflexEntity, t);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <N> String toSQL(N t1) throws Exception{
		StringBuilder sql=new StringBuilder();
		sql.append("select ");
		sql.append(selectQueryField());
		sql.append("   from   ");
		sql.append(getTables());
		return sql.toString();

	}


	@Override
	public BaseSqlAnalysis<T> getBaseSqlAnalysis() {
		// TODO Auto-generated method stub
		if (baseSqlAnalysis!=null){
			return baseSqlAnalysis;
		}
		HashMap<String,TableColumnEntity> hashMap= reflexEntity.getForeignkeyColumnMap();
		Collection<TableColumnEntity> list= hashMap.values();
		int i=0;
		for (TableColumnEntity tableColumnEntity:list){
			if (tableColumnEntity.isHierarchicalQueries()){
				i++;
			}
		}
		if(i==0){//该表中没有外键
			return    baseSqlAnalysis=new  GeneralQueryAllSqlAnalysis<T>(reflexEntity,t);
		}else{
			return    baseSqlAnalysis=new  HierarchicalQueryAllAnalysis<T>(reflexEntity, t);
		}

	}

}
