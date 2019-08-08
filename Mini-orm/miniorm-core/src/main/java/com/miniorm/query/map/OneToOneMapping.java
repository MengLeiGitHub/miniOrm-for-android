package com.miniorm.query.map;

import com.miniorm.dao.BaseDao;
import com.miniorm.dao.builder.Where;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.entity.TableIdEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by admin on 2017-02-28.
 */

public class OneToOneMapping extends ToOneMapping{




    @Override
    protected Object thisObjectNoForeignKey(final Object thisObject, Class<? extends Object> returnclass, BaseDao baseDao2) throws Exception {
        ReflexEntity thisreflexEntity = ReflexCache.getReflexEntity(thisObject.getClass().getName());
        ReflexEntity targetreflexEntity = ReflexCache.getReflexEntity(returnclass.getName());
        HashMap<String, TableColumnEntity> targetReflexForeignkeyColumnMap = targetreflexEntity.getForeignkeyColumnMap();
        String foreignKeyColumn = null;
        Field foreignkeyfield=null;
        for (String key : targetReflexForeignkeyColumnMap.keySet()) {
            TableColumnEntity tableColumnEntity = targetReflexForeignkeyColumnMap.get(key);
            foreignkeyfield = tableColumnEntity.getField();
            if (foreignkeyfield.getType() == thisObject.getClass()) {
                foreignkeyfield = tableColumnEntity.getField();
                foreignKeyColumn = tableColumnEntity.getColumnName();

            } else {
                continue;
            }
        }
        if (foreignkeyfield == null) {
            throw new Exception("必须在两个表中的一个，设置外键关联另外一个表");

        }
        Field idfield = thisreflexEntity.getTableIdEntity().getField();
        Object id = new EntityParse<>(null).getObjFromField(idfield, thisObject);

        return baseDao2.getQueryBuilder().callQuery().queryAll().where(Where.handle().eq(foreignKeyColumn, id.toString())).excute();

    }


}
