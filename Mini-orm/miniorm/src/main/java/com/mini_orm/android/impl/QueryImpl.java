package com.mini_orm.android.impl;


import com.mini_orm.android.KeyWork;
import com.mini_orm.dao.database.QueryInterface;
import com.mini_orm.dao.utils.ReflexEntity;
import com.mini_orm.entity.TableColumnEntity;
import com.mini_orm.entity.TableIdEntity;

public class QueryImpl implements QueryInterface {

	public String queryById(int id, ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub
		String tableName = reflexEntity.getTableEntity().getTableName();
		StringBuilder sb = new StringBuilder();
		sb.append("select  * from   ");
		sb.append(tableName);
		sb.append("  where  ");
		String tableid = reflexEntity.getTableIdEntity().getColumnName();
		sb.append(tableid);
		sb.append(" =");
		sb.append(id);
		sb.append(" ;");
		return sb.toString();
	}

	public String queryById(String id, ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub

		String tableName = reflexEntity.getTableEntity().getTableName();
		StringBuilder sb = new StringBuilder();
		sb.append("select  * from   ");
		sb.append(tableName);
		sb.append("  where  ");
		String tableid = reflexEntity.getTableIdEntity().getColumnName();
		sb.append(tableid);
		sb.append(" = '");
		sb.append(id);
		sb.append("';");

		return sb.toString();

	}

	public <N> String queryByEntity(N t, ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();
		sb.append(" select  *   from  ");

		sb.append(reflexEntity.getTableName());

		TableIdEntity tableid = reflexEntity.getTableIdEntity();

		if (tableid != null
				&& tableid.getDefaultVal() != ((Integer) tableid.getColumnVal())) {
			sb.append(KeyWork.WHERE);
			sb.append(tableid.getName());
			sb.append("=");
			sb.append(tableid.getColumnVal().toString());

		} else {
			int size = reflexEntity.getTableColumnMap().values().size();

			if (size != 0)
				sb.append(KeyWork.WHERE);
			
			TableIdEntity id=reflexEntity.getTableIdEntity();
			if(id!=null&&id.getDefaultVal()!=((Integer)id.getColumnVal()).intValue()){
				sb.append(id.getColumnName());
				sb.append("=");
				sb.append(id.getColumnVal());
			}else{
			
			
					for (TableColumnEntity tableColumnEntity : reflexEntity.getTableColumnMap().values()) {
						
						String key= tableColumnEntity.getColumnName();
						
						Object obj = tableColumnEntity.getColumnVal();
		 				
						String s=keyVal(key,obj);
						sb.append(s==null?"":s);
					}
					if (size != 0) {
						sb.delete(sb.length() - KeyWork.AND.length(), sb.length() - 1);
					}
			}
		}

		sb.append(";");

		return sb.toString();
	}
	
	private String keyVal(String key,Object val){
		StringBuilder  sb=new StringBuilder();
		if (val == null)
			   return null;
		if (val instanceof String)
			sb.append(key + "=" + "'" + val + "'  " + KeyWork.AND);
		else
			sb.append(key + "=" + val + KeyWork.AND);
		
		return sb.toString();
	}
	

	public String queryAll(ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();

		sb.append("select  * from   ");

		sb.append(reflexEntity.getTableName());

		String[] condations = reflexEntity.getCondition();
		if (condations != null && condations.length != 0) {
			sb.append(KeyWork.WHERE);

			for (String condation : condations) {
				sb.append(" " + condation + " ");
				sb.append(KeyWork.AND);
			}
			
			sb.delete(sb.length()-KeyWork.AND.length()-1 ,sb.length()-1);
			
		}

		sb.append(";");

		return sb.toString();
	}

}
