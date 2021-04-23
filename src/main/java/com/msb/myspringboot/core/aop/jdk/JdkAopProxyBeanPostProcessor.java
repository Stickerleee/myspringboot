/**
 * 
 */
package com.msb.myspringboot.core.aop.jdk;

import com.msb.myspringboot.core.aop.intercept.Interceptor;
import com.msb.myspringboot.core.aop.process.AbstractAopProxyBeanPostProcessor;

/**
 * AOP JDK代理包装执行器
 * 
 * @author Stickerleee
 * @since 2021年4月22日 上午10:14:44
 */
public class JdkAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {

	@Override
	public Object wrapperBean(Object target, Interceptor interceptor) {
		return JdkInvocationHandler.wrap(target, interceptor);
	}

}
