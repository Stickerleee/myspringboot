/**
 * 
 */
package com.msb.myspringboot.core.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.msb.myspringboot.core.aop.intercept.Interceptor;
import com.msb.myspringboot.entity.MethodInvocation;

/**
 * AOP 动态生成的JDK执行类
 * 
 * @author Stickerleee
 * @since 2021年4月22日 上午10:33:23
 */
public class JdkInvocationHandler implements InvocationHandler {

	/**
	 * 目标对象
	 */
	private final Object target;

	/**
	 * 执行的拦截器
	 */
	private final Interceptor interceptor;

	private JdkInvocationHandler(Object target, Interceptor interceptor) {
		this.target = target;
		this.interceptor = interceptor;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		MethodInvocation methodInvocation = new MethodInvocation(target, method, args);
		return interceptor.intercept(methodInvocation);
	}

	/**
	 * 包装目标Bean为代理
	 * 
	 * @param target
	 * @param interceptor
	 * @return
	 */
	public static Object wrap(Object target, Interceptor interceptor) {
		JdkInvocationHandler jdkInvocationHandler = new JdkInvocationHandler(target, interceptor);
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
				jdkInvocationHandler);
	}

}
