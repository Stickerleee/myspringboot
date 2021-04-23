package com.msb.demo.aspect;

import com.msb.myspringboot.annotation.aop.After;
import com.msb.myspringboot.annotation.aop.Aspect;
import com.msb.myspringboot.annotation.aop.Before;
import com.msb.myspringboot.annotation.aop.Pointcut;
import com.msb.myspringboot.core.aop.lang.JoinPoint;

@Aspect
public class TeacherAspect {

	@Pointcut(value = "com.msb.demo.TeacherServiceImpl")
	public void aspect() {

	}

	@Before
	public void beforeAction(JoinPoint joinPoint) {
		System.out.println("aspect teacher before to do something");
	}

	@After
	public void afterAction(Object result, JoinPoint joinPoint) {
		System.out.println("Now in Aspect and get result: " + result);
		System.out.println("aspect teacher after to do something");
	}

}
