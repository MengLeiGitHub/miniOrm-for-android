package com.miniorm.android.impl;

import com.miniorm.constant.ParamConstant;
import com.miniorm.entity.TableColumnEntity;

/**
 * Created by admin on 2017-04-09.
 */

public class FieldConditionUtils {

    public static   StringBuilder  appVal(StringBuilder sb, String key, Object obj, String end){
        if (obj instanceof String) {
            sb.append(key);
            sb.append(" = ");

            sb.append("'");
            sb.append(obj.toString());
            sb.append("'");
            sb.append(end);

        }	else if(obj instanceof Boolean ){
/*
			if(!tableColumnEntity.isIgnoreBooleanParam()){
*/
            sb.append(key);
            sb.append(" = ");
            if(((Boolean) obj).booleanValue())
                sb.append(ParamConstant.BOOLEAN_TRUE  );
            else
                sb.append(ParamConstant.BOOLEAN_FALSE );
            sb.append(end);
/*
			}
*/
        }else  if(obj instanceof Integer ){
            if(((Integer) obj).intValue()==0)
            {

            }
            else{
                sb.append(key);
                sb.append(" = ");
                sb.append(((Integer) obj).intValue());
                sb.append(end);

            }
        }

        else{
            sb.append(key);
            sb.append(" = ");
            sb.append(obj);
            sb.append(end);
        }
        return  sb;
    }
}
