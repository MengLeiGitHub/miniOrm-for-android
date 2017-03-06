package com.miniorm.aopdemo;

import com.miniorm.annotation.ManyToMany;
import com.miniorm.annotation.ManyToOne;
import com.miniorm.annotation.OneToMany;
import com.miniorm.annotation.OneToOne;
import com.miniorm.dao.reflex.EntityParse;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.query.map.BaseMap;
import com.miniorm.query.map.OneToManyMapping;
import com.miniorm.query.map.ToOneMapping;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Aspect representing the cross cutting-concern: Method and Constructor Tracing.
 */
@Aspect
public class ToOneAspect {

  private static final String POINTCUT_METHOD =
      "execution(@com.miniorm.annotation.ManyToOne * *(..))";
  private static final String POINTCUT_METHOD2 =
          "execution(@com.miniorm.annotation.OneToOne * *(..))";


  @Pointcut(POINTCUT_METHOD)
  public void methodAnnotatedWithDebugTrace() {}

  @Pointcut(POINTCUT_METHOD2)
  public void methodAnnotatead2OnToOne() {}


  /*
  private static final String POINTCUT_CONSTRUCTOR =
          "execution(@com.miniorm.aopdemo *.new(..))";

  @Pointcut(POINTCUT_CONSTRUCTOR)
  public void constructorAnnotatedDebugTrace() {}*/

  @Around("methodAnnotatedWithDebugTrace()  || methodAnnotatead2OnToOne()")
  public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
   // String methodName = methodSignature.getName();
    Method method= methodSignature.getMethod();
   // Object object= joinPoint.getTarget();
  //  Object o=joinPoint.getThis();
    BaseMap baseMap = null;
    ManyToOne  manyToMany= method.getAnnotation(ManyToOne.class);
    if(manyToMany!=null){
      baseMap=new ToOneMapping();
      com.miniorm.debug.DebugLog.e( "123333333333333ManyToOne" );
    }else {
      if(method.getAnnotation(OneToOne.class)!=null){
        baseMap=new ToOneMapping();
      }
    }
    Object  result=null;
    if(baseMap!=null){
      result=  baseMap.proceedFilterToQuery(joinPoint.getThis(),method);
     /* QueryRecorder.isRecordered(joinPoint.getThis().toString(),result.toString());
      ReflexEntity thisreflexEntity= ReflexCache.getReflexEntity(joinPoint.getThis().getClass().getName());
      HashMap<String,TableColumnEntity> targetReflexForeignkeyColumnMap= thisreflexEntity.getForeignkeyColumnMap();
      Field foreignkeyfield=null;
      for (String key : targetReflexForeignkeyColumnMap.keySet()) {//
        TableColumnEntity tableColumnEntity = targetReflexForeignkeyColumnMap.get(key);
        foreignkeyfield=tableColumnEntity.getField();
        if(foreignkeyfield.getType()==method.getReturnType()){
          foreignkeyfield=tableColumnEntity.getField();
          new EntityParse<>(joinPoint.getThis()).setEntityValue(joinPoint.getThis(),result,foreignkeyfield);

        }else {
          continue;
        }
      }*/
    }

    return result;
  }


}
