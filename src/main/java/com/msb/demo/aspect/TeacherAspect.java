package com.msb.demo.aspect;

import com.msb.jsonboot.annotation.aop.After;
import com.msb.jsonboot.annotation.aop.Aspect;
import com.msb.jsonboot.annotation.aop.Before;
import com.msb.jsonboot.annotation.aop.Pointcut;
import com.msb.jsonboot.annotation.ioc.Component;
import com.msb.jsonboot.core.aop.lang.JoinPoint;

@Aspect
public class TeacherAspect {

    @Pointcut(value = "com.df.demo.TeacherServiceImpl*")
    public void aspect(){

    }

    @Before
    public void beforeAction(JoinPoint joinPoint) {
        System.out.println("aspect teacher before to do something");
    }

    @After
    public void afterAction(Object result, JoinPoint joinPoint) {
        System.out.println(result);
        System.out.println("aspect teacher after to do something");
    }

}
