package com.miniorm.query.parse;

import java.util.List;




import com.miniorm.dao.reflex.ReflexEntity;

public abstract class BaseResultParse<N> {
	protected  boolean   Alias=true;

	public   BaseResultParse<N>  useAlias(boolean Alias){
			this.Alias=Alias;
		return  this;
	}

	public abstract <T> T parse(N n,  Class<T> t, ReflexEntity reflexEntity)
			throws Exception;

	public abstract <T> List<T> parseList(N n, Class<T> t, ReflexEntity reflexEntity)
			throws Exception;

    public abstract <T> int ParseLastInsertRowId(N n,  Class<T> t, ReflexEntity reflexEntity) ;
}
