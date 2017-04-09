package com.miniorm.query.analysis.general;

import java.lang.reflect.Field;

import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.query.analysis.SQL;

/**
 * Created by ML on 2017-02-15.
 */

public class GeneralQueryByIdSqlAnalysis<T> extends GeneralSqlAnalysis<T> {

	public GeneralQueryByIdSqlAnalysis(ReflexEntity reflexEntity,
									   Class<T> n) {
		super(reflexEntity, n);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SQL FieldCondition(Object t) throws Exception {

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
		}
		return null;


	}
}
