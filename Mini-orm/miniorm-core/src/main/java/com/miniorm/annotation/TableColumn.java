package com.miniorm.annotation;

import com.miniorm.constant.ParamConstant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface TableColumn {
    /**
     * 列名
     * @return
     */
    public String name();


    public String  defaultVal()  default ParamConstant.ColumnDefaultValue;

    /**
     * 列类型
     * @return
     */
    public String columnType();

    /**
     * 是否为主键
     * @return
     */

    public boolean isPrimaryKey() default false;

    /**
     * 是否为外键
     * @return
     */
    public boolean isForeignkey() default false;


    /**
     *级联查询时
     * @return
     */
    public boolean HierarchicalQueries() default false;

    /**
     * 是否在查询时，Boolean属性更新时要关联到到这个属性
     * @return
     */
    public boolean IgnoreBooleanParam() default true;

}
