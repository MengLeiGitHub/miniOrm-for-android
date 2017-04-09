package com.miniorm.query.analysis;

import java.lang.reflect.Field;

import com.miniorm.android.KeyWork;
import com.miniorm.annotation.Table;
import com.miniorm.constant.ParamConstant;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.dao.utils.StringUtils;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.entity.TableIdEntity;
import com.miniorm.query.analysis.hierarchical.HierarchicalQueryByIdSqlAnalysis;

/**
 * Created by ML on 2017-02-15.
 */

public abstract class BaseSqlAnalysis<N> {

    protected ReflexEntity reflexEntity;

	Class<N>  t;
  
    public BaseSqlAnalysis(ReflexEntity reflexEntity,Class<N> n){
    		this.t=n;
    		this.reflexEntity=reflexEntity;
    }
    
    
    public ReflexEntity getReflexEntity() {
        return reflexEntity;
    }

    public BaseSqlAnalysis(ReflexEntity reflexEntity) {
        this.reflexEntity = reflexEntity;
    }

    /**
     * 查询的字段
     *
     * @return
     */
    public abstract SQL selectQueryField();

    /**
     * 查询的表
     *
     * @return
     */
    public abstract SQL fromTables();

    /**
     *
     * @param o  解析查询条件
     * @return
     */
	public  <T> SQL FieldCondition(T o) throws Exception {
		SQL sqlO=CheckID(reflexEntity,o);
		if(sqlO!=null){//先 判断主键 是否为空，外键可能设置的也有值
			StringBuilder Id=new StringBuilder();
			Id.append(reflexEntity.getTableName());
			Id.append(".");
			Id.append(reflexEntity.getTableIdEntity().getColumnName());
			Id.append("=");
			Id.append(sqlO.toSQL());


			return new SQL(Id.toString());
		}
		StringBuilder sb = new StringBuilder();
		StringBuilder waiSb=getOther(reflexEntity,o,true);
		sb.append(waiSb);

		return new SQL(sb.toString());
	}
	protected String keyVal(String key,Object val,TableColumnEntity tableColumnEntity,boolean flag) throws Exception{
		StringBuilder  sb=new StringBuilder();
		if (val == null)
			return null;
		if (val instanceof String)
			sb.append(key + "=" + "'" + val + "'  " );
		else if(val instanceof Boolean ){
			if(!tableColumnEntity.isIgnoreBooleanParam()){
				if(((Boolean) val).booleanValue())
					sb.append(key + "=  "+ ParamConstant.BOOLEAN_TRUE );
				else
					sb.append(key + "="+ParamConstant.BOOLEAN_FALSE);

			}
		}else  if(val instanceof Integer ){
			if(((Integer) val).intValue()==0)
			{

			}
			else{
				sb.append(key + "=   "+((Integer) val).intValue() );
			}

		}else  if(val instanceof Long ){
			if(((Long) val).intValue()==Long.parseLong(tableColumnEntity.getDefaultVal()))
			{

			}
			else{
				sb.append(key + "=   "+((Long) val).intValue() );
			}

		}
		else{

			Table t=val.getClass().getAnnotation(Table.class);
			if(t==null){
				sb.append(key + "=" + val );

			}else{
				if(!flag)return sb.toString();
				if(!tableColumnEntity.isHierarchicalQueries())return sb.toString();
				ReflexEntity  fieldObjReflex= ReflexCache.getReflexEntity(tableColumnEntity.getField().getType().getName());

				SQL sqlO=CheckID(fieldObjReflex,val);
				if(sqlO==null){
					StringBuilder other=getOther(fieldObjReflex,val,false);
					sb.append(other);
				}else {
					StringBuilder Id=new StringBuilder();

					Id.append(fieldObjReflex.getTableName());
					Id.append(".");
					Id.append(fieldObjReflex.getTableIdEntity().getColumnName());
					Id.append("=");
					Id.append(sqlO.toSQL());
					sb.append(Id.toString());
				}
			}

			return sb.toString();
		}


		return sb.toString();
	}


	/**
	 * 检测 主键ID的值是否为空
	 * @param reflexEntity
	 * @param o
	 * @return
	 * @throws IllegalAccessException
	 */


	private  <N> SQL    CheckID(ReflexEntity reflexEntity,N o) throws Exception {
		TableIdEntity tableIdEntity=reflexEntity.getTableIdEntity();

		Field fieldId= tableIdEntity.getField();
		fieldId.setAccessible(true);
		Object ID=fieldId.get(o);
		if(ID!=null){
			boolean isFalg=false;
			if(ID instanceof Integer ){
				if(((Integer)ID)!=tableIdEntity.getDefaultVal()){
					isFalg=true;
				}

			}else if(ID instanceof  Long){
				if(((Long)ID)!=tableIdEntity.getDefaultVal()){
					isFalg=true;
				}
			}
			else if(ID instanceof String){
				if(StringUtils.isNull(ID.toString())){
					isFalg=true;
				}

			}
			if(isFalg)
				return 	new HierarchicalQueryByIdSqlAnalysis<N>(reflexEntity,null).FieldCondition(ID);

		}
		return null;
	}

	/**
	 *
	 * @param reflexEntity
	 * @param o
	 * @param flag  表示是否执行外键 键值关联
	 * @return
	 * @throws IllegalAccessException
	 */

	private <N> StringBuilder   getOther(ReflexEntity reflexEntity,N o,boolean flag) throws Exception{
		StringBuilder sb = new StringBuilder();

		int size = reflexEntity.getTableColumnMap().values().size();
		for (TableColumnEntity tableColumnEntity : reflexEntity
				.getTableColumnMap().values()) {

			Object obj = EntityParse.getFieldObjectVal(o,
					tableColumnEntity.getField());
			if (obj == null) continue;

			String key = reflexEntity.getTableName() + "."
					+ tableColumnEntity.getColumnName();
			String s = keyVal(key, obj, tableColumnEntity,flag);
			sb.append(s == null ? "" :s);
			if(!StringUtils.isNull(s))
				sb.append(KeyWork.AND);
		}

		if (size != 0) {
			if(sb.toString().endsWith(KeyWork.AND))
				sb.delete(sb.length() - KeyWork.AND.length(), sb.length() - 1);
		}

		return sb;
	}

    /* public <D>  SQL FieldCondition(D o)  throws Exception {
    	StringBuilder sb=new StringBuilder();
    	ReflexEntity reflexEntity=getReflexEntity();
    	TableIdEntity tableid = reflexEntity.getTableIdEntity();

		if (tableid != null
				&& tableid.getDefaultVal() != ((Integer) tableid.getColumnVal())) {
			sb.append(reflexEntity.getTableName());
			sb.append(".");
			sb.append(tableid.getName());
			sb.append("=");
			Object  id=EntityParse.getFieldObjectVal(o, tableid.getField());
			if(id  instanceof String){
				sb.append("'");
				sb.append(id.toString());
				sb.append("'");
			}else
				sb.append(id.toString());

		} else {
			
			TableIdEntity id=reflexEntity.getTableIdEntity();
			if(id!=null&&id.getDefaultVal()!=((Integer)id.getColumnVal()).intValue()){
				sb.append(reflexEntity.getTableName());
				sb.append(".");
				sb.append(id.getColumnName());
				sb.append("=");
				sb.append(id.getColumnVal());
			}else{
				    int size=reflexEntity.getTableColumnMap().values().size();
				    for (TableColumnEntity tableColumnEntity : reflexEntity.getTableColumnMap().values()) {
						
						String key= reflexEntity.getTableName()+"."+tableColumnEntity.getColumnName();
							
						Object obj = EntityParse.getFieldObjectVal(o, tableColumnEntity.getField());
								if(obj==null)continue;
						String s=keyVal(key,obj,tableColumnEntity);

						sb.append(s==null?"":s);
					}
					if (size != 0) {
						if(sb.toString().endsWith(KeyWork.AND))
						sb.delete(sb.length() - KeyWork.AND.length(), sb.length() - 1);
					}
			}
		}

        return new SQL(sb.toString());
    }

    protected String keyVal(String key,Object val,TableColumnEntity tableColumnEntity) throws Exception{
		StringBuilder  sb=new StringBuilder();
		if (val == null)
			   return null;
		if (val instanceof String)
			sb.append(key + "=" + "'" + val + "'  " + KeyWork.AND);
		else if(val instanceof Boolean ){
					if(!tableColumnEntity.isIgnoreBooleanParam()){
						if(((Boolean) val).booleanValue())
							sb.append(key + "=  "+ ParamConstant.BOOLEAN_TRUE + KeyWork.AND);
						else
							sb.append(key + "="+ParamConstant.BOOLEAN_FALSE+ KeyWork.AND);
					}
		}else  if(val instanceof Integer ){
			if(((Integer) val).intValue()==0)
			{

			}
			else
				sb.append(key + "=   "+((Integer) val).intValue() + KeyWork.AND);
		}
		else{
			
			Table t=val.getClass().getAnnotation(Table.class);
			if(t==null){
				sb.append(key + "=" + val + KeyWork.AND);

			}else{
				 ReflexEntity  fieldObjReflex= ReflexCache.getReflexEntity(val.getClass().getName());
                 TableIdEntity tableIdEntity=fieldObjReflex.getTableIdEntity();
                 Field field1=tableIdEntity.getField();
                 field1.setAccessible(true);
                 Object objects=field1.get(val);
                 
                 if(objects==null){
                	 
                	 
                	 
                	 
                	 
                	 
                	 
                 }
                 
                 
                 if(objects instanceof Integer){
                	 if(((Integer) objects).intValue() != ((Integer)tableIdEntity.getDefaultVal()).intValue()){
                	//	 System.err.println(objects+"  "+((Integer)tableIdEntity.getDefaultVal()));
                		 sb.append(key + "=" + objects.toString() + KeyWork.AND);
                	 }
                 }else if(objects instanceof String && objects!=null){
                	 	if(StringUtils.isNull(objects.toString())){
        	 				 sb.append(key + "='" + objects.toString() +"'"+ KeyWork.AND);

                	 	}
                 }
                 
 				
			}
			
			
		}
			
		
		return sb.toString();
	}
*/
}
