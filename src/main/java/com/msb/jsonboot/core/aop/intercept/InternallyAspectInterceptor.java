/**
 * 
 */
package com.msb.jsonboot.core.aop.intercept;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.msb.jsonboot.annotation.aop.*;
import com.msb.jsonboot.core.aop.lang.*;
import com.msb.jsonboot.entity.MethodInvocation;
import com.msb.jsonboot.utils.PatternMatchUtil;
import com.msb.jsonboot.utils.ReflectionUtil;

import groovyjarjarantlr4.v4.runtime.misc.Utils;

/**
 * AOP 实现类
 * 
 * @author Stickerleee
 * @since 2021年4月21日 下午4:35:07
 */
public class InternallyAspectInterceptor extends Interceptor {

	/**
	 * 当前切面Bean
	 */
	private final Object aspectBean;
	
	/**
	 * 存储切入点的Set
	 */
	private final HashSet<String> pointCutUrls = new HashSet<>();
	
	/**
	 * 存储@before前置方法的数组
	 */
	private final List<Method> beforeMethod = new ArrayList<>();
	
	/**
	 * 存储@after后置方法的数组
	 */
	private final List<Method> afterMethod = new ArrayList<>();
	
	public InternallyAspectInterceptor(Object aspectBean) {
		this.aspectBean = aspectBean;
		init();
	}
	
	/**
	 * 初始化AOP实例，整合Bean中的方法
	 */
	private void init() {
		//循环当前Bean中的所有方法
		for (Method method : aspectBean.getClass().getMethods()) {
			Pointcut pointcut = method.getAnnotation(Pointcut.class);
			if (!Objects.isNull(pointcut) && StringUtils.isNotBlank(pointcut.value())){
                pointCutUrls.add(pointcut.value());
            }
            Before before = method.getAnnotation(Before.class);
            if (!Objects.isNull(before)){
                beforeMethod.add(method);
            }
            After after = method.getAnnotation(After.class);
            if (!Objects.isNull(after)){
                afterMethod.add(method);
            }
		}
	}

    @Override
    public boolean supports(Object bean) {
        return pointCutUrls.stream().anyMatch(url -> PatternMatchUtil.simpleMatch(url,
        			bean.getClass().getName())) && (!beforeMethod.isEmpty() || !afterMethod.isEmpty());
    }


    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        JoinPoint joinPoint = new JoinPointImpl(aspectBean, methodInvocation.getTargetObject(), methodInvocation.getArgs());
        beforeMethod.forEach(method -> {
            //与调用的方法参数要一致
            ReflectionUtil.executeMethodNoResult(aspectBean, method, joinPoint);
        });
        Object result = methodInvocation.proceed();
        afterMethod.forEach(method -> {
            ReflectionUtil.executeMethodNoResult(aspectBean, method, result, joinPoint);
        });
        return result;
    }

}
