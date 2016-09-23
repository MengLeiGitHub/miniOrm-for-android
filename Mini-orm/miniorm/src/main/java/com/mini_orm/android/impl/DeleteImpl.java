package com.mini_orm.android.impl;


import com.mini_orm.android.KeyWork;
import com.mini_orm.dao.database.DeleteInterface;
import com.mini_orm.dao.utils.ReflexEntity;
import com.mini_orm.entity.TableColumnEntity;
import com.mini_orm.entity.TableIdEntity;

import java.util.List;

public class DeleteImpl implements DeleteInterface {

	public <T> String delete(T t, ReflexEntity rexEntity) {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();
		sb.append(" DELETE FROM  ");
		sb.append(rexEntity.getTableName());
		sb.append("   WHERE   ");

		TableIdEntity tableIdEntity = rexEntity.getTableIdEntity();
		if (tableIdEntity != null) {
			sb.append(tableIdEntity.getColumnName());
			sb.append("=");
			sb.append(tableIdEntity.getColumnVal());
		} else {
			boolean isdeleteAnd = false;
			for (TableColumnEntity tableColumn : rexEntity.getTableColumnMap()
					.values()) {

				if (tableColumn.isPrimaryKey())
					continue;

				sb.append(tableColumn.getColumnName());
				sb.append(" = ");

				Object obj = tableColumn.getColumnVal();
				if (obj instanceof String) {
					sb.append("'");
					sb.append(obj.toString());
					sb.append("'");
				} else
					sb.append(obj);

				sb.append(KeyWork.AND);
				isdeleteAnd=true;
				 
			}
			if (isdeleteAnd) {
				sb.delete(sb.length() - KeyWork.AND.length(), sb.length() - 1);
			}
		}
		

		sb.append(";");

		return sb.toString();

	}

	public <T> String delete(List<T> t, ReflexEntity reflexEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> String deleteAll(T t, ReflexEntity reflexEntity) {

		StringBuilder sb = new StringBuilder();
		sb.append(" DELETE FROM  ");
		sb.append(reflexEntity.getTableName());

		sb.append(";");

		return sb.toString();
	}

}
