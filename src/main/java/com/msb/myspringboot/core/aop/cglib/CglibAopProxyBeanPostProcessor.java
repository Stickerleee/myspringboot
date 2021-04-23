/**
 * 
 */
package com.msb.myspringboot.core.aop.cglib;

import com.msb.myspringboot.core.aop.intercept.Interceptor;
import com.msb.myspringboot.core.aop.process.AbstractAopProxyBeanPostProcessor;

/**
 * Cglib 包装器
 * 
 * @author Stickerleee
 * @since 2021年4月22日 下午3:57:16
 */
public class CglibAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {

	@Override
	public Object wrapperBean(Object target, Interceptor interceptor) {
		return CglibMethodInterceptor.wrap(target, interceptor);
	}

}
