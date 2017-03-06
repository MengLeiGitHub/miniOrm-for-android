package com.miniorm.query.analysis.hierarchical;

import java.lang.reflect.Field;

import com.miniorm.annotation.Table;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.query.analysis.SQL;

/**
 * Created by ML on 2017-02-15.
 */

public class HierarchicalQueryByIdSqlAnalysis<T> extends HierarchicalQueriesSQLAnalysis<T> {

	public HierarchicalQueryByIdSqlAnalysis(ReflexEntity reflexEntity, T n) {
		super(reflexEntity, n);
		// TODO Auto-generated constructor stub
	}






	@Override
	public <T> SQL FieldCondition(T t) throws IllegalArgumentException,
			IllegalAccessException {
		// TODO Auto-generated method stub
		Field field = reflexEntity.getTableIdEntity().getField();

		if(field.getType()==String.class){
			return new SQL("'"+t.toString()+"'");
		}else if(field.getType()==int.class){
			return new SQL(""+t);
		}else if (field.getType()==long.class){
			return	new   SQL(t+"");
		}else if(field.getType()==Integer.class){
			return new SQL(t.toString());
		}else if(field.getType()==Long.class){
			return  new SQL(t.toString());
		}else{
			Table table=field.getAnnotation(Table.class);
			if(table!=null){
				field.setAccessible(true);
				Object fieldVal = field.get(t);
				return  FieldCondition(fieldVal);
			}
		}
		return null;




/*
		*/
	}

}
