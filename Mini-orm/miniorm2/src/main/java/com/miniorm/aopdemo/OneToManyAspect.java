package com.miniorm.aopdemo;

import com.miniorm.annotation.ManyToMany;
import com.miniorm.annotation.ManyToOne;
import com.miniorm.annotation.OneToMany;
import com.miniorm.annotation.OneToOne;
import com.miniorm.query.map.BaseMap;
import com.miniorm.query.map.OneToManyMapping;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Aspect representing the cross cutting-concern: Method and Constructor Tracing.
 */
@Aspect
public class OneToManyAspect {

  private static final String POINTCUT_METHOD =
      "execution(@com.miniorm.annotation.OneToMany * *(..))";

  private static final String POINTCUT_CONSTRUCTOR =
      "execution(@com.miniorm.aopdemo *.new(..))";

  @Pointcut(POINTCUT_METHOD)
  public void methodAnnotatedWithDebugTrace() {}

  @Pointcut(POINTCUT_CONSTRUCTOR)
  public void constructorAnnotatedDebugTrace() {}




  @Around("methodAnnotatedWithDebugTrace()")
  public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method method= methodSignature.getMethod();
    BaseMap baseMap = null;
    OneToMany  manyToMany= method.getAnnotation(OneToMany.class);
    if(manyToMany!=null){
      baseMap=new OneToManyMapping();
    }
    Object  result=joinPoint.proceed();
    if(baseMap!=null&&result==null){
      result=  baseMap.proceedFilterToQuery(joinPoint.getThis(),method);
    }

    return result;
  }

}
