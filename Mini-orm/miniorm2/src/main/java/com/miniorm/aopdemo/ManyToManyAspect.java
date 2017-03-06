package com.miniorm.aopdemo;

import com.miniorm.annotation.ManyToMany;
import com.miniorm.annotation.ManyToOne;
import com.miniorm.annotation.OneToMany;
import com.miniorm.annotation.OneToOne;
import com.miniorm.query.map.BaseMap;
import com.miniorm.query.map.ManyToManyMapping;
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
public class ManyToManyAspect {

  private static final String POINTCUT_METHOD =
      "execution(@com.miniorm.annotation.ManyToMany * *(..))";

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
     Object o=joinPoint.getThis();
    BaseMap baseMap = null;
    ManyToMany  manyToMany= method.getAnnotation(ManyToMany.class);
    if(manyToMany!=null){
      baseMap=new ManyToManyMapping();
    }
    Object  result=joinPoint.proceed();
    if(baseMap!=null&&result==null)
             result=  baseMap.proceedFilterToQuery(joinPoint.getThis(),method);
    //Object result = joinPoint.proceed();
    return result;
  }

  /**
   * Create a log message.
   *
   * @param methodName A string with the method name.
   * @param methodDuration Duration of the method in milliseconds.
   * @return A string representing message.
   */
  private static String buildLogMessage(String methodName, long methodDuration) {
    StringBuilder message = new StringBuilder();
    message.append("Gintonic --> ");
    message.append(methodName);
    message.append(" --> ");
    message.append("[");
    message.append(methodDuration);
    message.append("ms");
    message.append("]");

    return message.toString();
  }
}
