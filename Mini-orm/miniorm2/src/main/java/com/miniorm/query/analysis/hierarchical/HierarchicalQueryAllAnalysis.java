package com.miniorm.query.analysis.hierarchical;

import com.miniorm.android.KeyWork;
import com.miniorm.annotation.Table;
import com.miniorm.constant.ParamConstant;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.dao.utils.StringUtils;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.entity.TableIdEntity;
import com.miniorm.query.analysis.SQL;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * 级联查询通过非主键查询SQL语句解析
 Created by ML on 2017-02-15.
 */

public class HierarchicalQueryAllAnalysis<T> extends
		HierarchicalQueryByOtherAnalysis<T> {


	public HierarchicalQueryAllAnalysis(ReflexEntity reflexEntity, T n) {
		super(reflexEntity, n);
		// TODO Auto-generated constructor stub
	}

	@Override
	public  <T> SQL FieldCondition(T o) throws Exception {
		return  new SQL("");
	}



}
