package com.miniorm.annotation;


import com.miniorm.dao.BaseDao2;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ManyToMany {
    public Class<?>  bridgingTable() ;

}
