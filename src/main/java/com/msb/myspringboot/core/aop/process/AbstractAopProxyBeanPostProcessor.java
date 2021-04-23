/**
 * 
 */
package com.msb.myspringboot.core.aop.process;

import java.util.Set;

import com.msb.myspringboot.annotation.aop.Aspect;
import com.msb.myspringboot.core.aop.intercept.Interceptor;
import com.msb.myspringboot.core.aop.intercept.InternallyAspectInterceptor;
import com.msb.myspringboot.core.ioc.BeanFactory;

/**
 * AOP 抽象类
 * 
 * @author Stickerleee
 * @since 2021年4月22日 上午10:01:11
 */
public abstract class AbstractAopProxyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean) {
		Object proxyBean = bean;
		Set<Object> aspectBeans = BeanFactory.getBeansByName(Aspect.class.getName());
		// 遍历AspectBeans的列表，并判断目标bean是否符合该aspect
		for (Object aspectBean : aspectBeans) {
			InternallyAspectInterceptor interceptor = new InternallyAspectInterceptor(aspectBean);
			if (interceptor.supports(bean)) {
				// 进行代理封装
				proxyBean = wrapperBean(bean, interceptor);
			}
		}
		return proxyBean;
	}

	/**
	 * 根据代理方式包装目标bean
	 * 
	 * @param target
	 * @param interceptor
	 * @return
	 */
	public abstract Object wrapperBean(Object target, Interceptor interceptor);

}
