package com.miniorm.query.analysis.hierarchical;

import com.miniorm.android.KeyWork;
import com.miniorm.dao.reflex.ProxyCache;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.query.analysis.BaseSqlAnalysis;
import com.miniorm.query.analysis.SQL;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * 级联查询外键对象生成sql语句解析
 * Created by ML on 2017-02-15.
 */

public abstract class HierarchicalQueriesSQLAnalysis<N> extends
		BaseSqlAnalysis<N> {


	public HierarchicalQueriesSQLAnalysis(ReflexEntity reflexEntity, Class<N> n) {
		super(reflexEntity, n);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SQL selectQueryField() {
		ReflexEntity reflexEntity = getReflexEntity();

		String sql=selectQueryField(reflexEntity, true);

		return new SQL(sql);
	}

	@Override
	public SQL fromTables() {
		ReflexEntity reflexEntity=getReflexEntity();
		HashMap<String, TableColumnEntity> tableColumnMap = reflexEntity
				.getForeignkeyColumnMap();
		StringBuilder stringBuilder=new StringBuilder();
		if(tableColumnMap.size()!=0){
			String  tableName=reflexEntity.getTableName();
			stringBuilder.append(" ");
			stringBuilder.append(tableName);
			stringBuilder.append(" ");
		}
		StringBuilder where=new StringBuilder();
		for (String key : tableColumnMap.keySet()) {
			TableColumnEntity tableColumnEntity = tableColumnMap.get(key);

			//&&tableColumnEntity.isHierarchicalQueries()
			if(tableColumnEntity.isForeignkey()){
				stringBuilder.append(getFromTables(tableColumnEntity,reflexEntity));
				if(where.length()==0){
					where.append(KeyWork.ON);
				}else{
					where.append(KeyWork.AND);
				}
				where.append(getTablesIdEquals(tableColumnEntity,reflexEntity));
			}
		}
		/*if(where.length()>0&&where.charAt(where.length()-1)=='='){
			where.deleteCharAt(where.length()-1);
		}
		*/
		return new SQL(stringBuilder.append("\n").append(where).append("\n").toString());
	}


	private String selectQueryField(ReflexEntity reflexEntity,
									boolean isHierarchical) {
		HashMap<String, TableColumnEntity> tableColumnMap = reflexEntity
				.getTableColumnMap();

		StringBuilder sql = new StringBuilder();
		String tableName = reflexEntity.getTableName();

		sql.append(tableName);
		sql.append(".");
		sql.append(reflexEntity.getTableIdEntity().getColumnName());
		sql.append(" as ");
		sql.append(tableName);
		sql.append("_");
		sql.append(reflexEntity.getTableIdEntity().getColumnName());
		sql.append(",");

		sql.append("\n");

		for (String key : tableColumnMap.keySet()) {
			TableColumnEntity tableColumnEntity = tableColumnMap.get(key);
			sql.append(tableName);
			sql.append(".");
			sql.append(tableColumnEntity.getColumnName());
			sql.append(" as ");
			sql.append(tableName);
			sql.append("_");
			sql.append(tableColumnEntity.getColumnName());
			sql.append(",");

 			if (tableColumnEntity.isForeignkey()
					&& isHierarchical) {
				String ClassNames=tableColumnEntity.getField().getType().getName();
				ReflexEntity reflexEntity2 = ReflexCache
						.getReflexEntity(ClassNames);

				if(reflexEntity2==null)continue;

				String sqls=	selectQueryField(reflexEntity2, false);
				sql.append(sqls);
				sql.append(",");

			} else {


			}
			sql.append("\n");

		}

		if (sql.length()-2>-1&&sql.charAt(sql.length() - 2) == ',') {
			sql.deleteCharAt(sql.length() - 2);
		}

		return sql.toString();
	}


	private String getFromTables(TableColumnEntity tableColumnEntity ,ReflexEntity reflexEntity){
		StringBuilder sb=new StringBuilder();
		Field f=tableColumnEntity.getField();

		if(tableColumnEntity.isForeignkey()){
			sb.append(" left join ");
			ReflexEntity reflexEntity2=ReflexCache.getReflexEntity( f.getType().getName());

			sb.append(reflexEntity2.getTableName());

		}else{
		/*	ManyToOne manyToOne=f.getAnnotation(ManyToOne.class);
			OneToOne one=f.getAnnotation(OneToOne.class);

			if(manyToOne==null&&one==null) return  sb.toString();

			if(manyToOne!=null){
				String key=manyToOne.defaultVal();
				sb.append(key);

			}else if(one!=null){

				String key=one.defaultVal();
				sb.append(key);
			}*/
		}


		return sb.toString();
	}

	private String getTablesIdEquals(TableColumnEntity tableColumnEntity,
									 ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub

		StringBuilder sb=new StringBuilder();
		sb.append(getTableID(reflexEntity,tableColumnEntity));
		Field f=tableColumnEntity.getField();

		ReflexEntity reflexEntity2=ReflexCache.getReflexEntity( f.getType().getName());

		sb.append("=");
		sb.append(getTableID(reflexEntity2));

		return sb.toString();

	}

	private  String getTableID(ReflexEntity reflexEntity,TableColumnEntity tableColumnEntity){
		StringBuilder sb=new StringBuilder();
		sb.append(reflexEntity.getTableName());
		sb.append(".");
		sb.append(tableColumnEntity.getColumnName());
		return sb.toString();
	}
	private  String getTableID(ReflexEntity reflexEntity){
		StringBuilder sb=new StringBuilder();
		sb.append(reflexEntity.getTableName());
		sb.append(".");
		sb.append(reflexEntity.getTableIdEntity().getColumnName());
		return sb.toString();
	}





}
