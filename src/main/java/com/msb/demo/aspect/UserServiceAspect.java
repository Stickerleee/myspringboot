package com.msb.demo.aspect;

import com.msb.jsonboot.annotation.aop.After;
import com.msb.jsonboot.annotation.aop.Aspect;
import com.msb.jsonboot.annotation.aop.Before;
import com.msb.jsonboot.annotation.aop.Pointcut;
import com.msb.jsonboot.annotation.ioc.Component;
import com.msb.jsonboot.core.aop.lang.JoinPoint;

@Aspect
public class UserServiceAspect {

    @Pointcut(value = "com.df.demo.UserService*")
    public void  userAspect(){

    }

    @Before
    public void before(JoinPoint joinPoint){
        System.out.println("这是userService的前置拦截器");
    }

    @After
    public void after(Object result, JoinPoint joinPoint){
        System.out.println("这是userService的后置拦截器");
    }

}
