package com.miniorm.annotation;


import com.miniorm.enumtype.Parmary;

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
public @interface TableID {

	 
	public Parmary type();
	
	public  String  columnType() ;
	
	
	public  String  name();
	
	public  int  defaultVal() default 0;
	
	public  boolean  isPrimaryKey() default true;
	
	
	
	
}
