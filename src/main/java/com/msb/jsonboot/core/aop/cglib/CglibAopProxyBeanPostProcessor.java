/**
 * 
 */
package com.msb.jsonboot.core.aop.cglib;

import com.msb.jsonboot.core.aop.intercept.Interceptor;
import com.msb.jsonboot.core.aop.process.AbstractAopProxyBeanPostProcessor;

/**
 * @author Stickerleee
 * @since 2021年4月22日 下午3:57:16
 */
public class CglibAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {

	@Override
	public Object wrapperBean(Object target, Interceptor interceptor) {
		return CglibMethodInterceptor.wrap(target, interceptor);
	}

}
