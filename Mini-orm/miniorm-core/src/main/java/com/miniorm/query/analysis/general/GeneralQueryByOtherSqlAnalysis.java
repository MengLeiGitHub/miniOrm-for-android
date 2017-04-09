package com.miniorm.query.analysis.general;

import java.lang.reflect.Field;

import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.dao.utils.StringUtils;
import com.miniorm.entity.TableIdEntity;
import com.miniorm.query.analysis.SQL;

/**
 * Created by ML on 2017-02-15.
 */

public class GeneralQueryByOtherSqlAnalysis<T> extends GeneralSqlAnalysis<T> {

	public GeneralQueryByOtherSqlAnalysis(ReflexEntity reflexEntity, Class<T> n) {
		super(reflexEntity, n);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 如果该对象的 主键id有值的话，将会按照主键查询
	 */
	@Override
	public <T> SQL FieldCondition(T o) throws Exception {
		// TODO Auto-generated method stub
		SQL sqlO=CheckID(reflexEntity,o);

		if(sqlO!=null){
			StringBuilder Id=new StringBuilder();
			Id.append(reflexEntity.getTableName());
			Id.append(".");
			Id.append(reflexEntity.getTableIdEntity().getColumnName());
			Id.append("=");
			Id.append(sqlO.toSQL());
			return new SQL(Id.toString());
		}
		return super.FieldCondition(o);
	}
	private  <N> SQL    CheckID(ReflexEntity reflexEntity,N o) throws Exception {
		TableIdEntity tableIdEntity=reflexEntity.getTableIdEntity();

		Field fieldId= tableIdEntity.getField();
		fieldId.setAccessible(true);
		Object ID=fieldId.get(o);
		if(ID!=null){
					Class<N> nClass= (Class<N>) o.getClass();
				return 	new  GeneralQueryByIdSqlAnalysis<N>(reflexEntity,nClass).FieldCondition(o);

		}
		return null;
	}
}
