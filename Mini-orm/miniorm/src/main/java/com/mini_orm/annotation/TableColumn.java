package com.mini_orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface TableColumn {

	public  String   name();
	
	public  String   columnType();
	
	public  boolean  isPrimaryKey() default false;
	
	public  boolean  isForeignkey() default false;
	
	
}
