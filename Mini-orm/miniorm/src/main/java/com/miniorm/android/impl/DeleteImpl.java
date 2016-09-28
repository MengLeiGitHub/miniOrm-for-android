package com.miniorm.android.impl;


import com.miniorm.android.KeyWork;
import com.miniorm.dao.database.DeleteInterface;
import com.miniorm.dao.utils.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.entity.TableIdEntity;

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
