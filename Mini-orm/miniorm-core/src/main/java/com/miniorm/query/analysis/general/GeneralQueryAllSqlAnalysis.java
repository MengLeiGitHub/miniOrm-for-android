package com.miniorm.query.analysis.general;

import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.query.analysis.SQL;

import java.lang.reflect.Field;

/**
 * Created by ML on 2017-02-15.
 */

public class GeneralQueryAllSqlAnalysis<T> extends GeneralSqlAnalysis<T> {

	public GeneralQueryAllSqlAnalysis(ReflexEntity reflexEntity,
									  Class<T> n) {
		super(reflexEntity, n);
		// TODO Auto-generated constructor stub
	}


}
