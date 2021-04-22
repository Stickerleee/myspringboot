/**
 * 
 */
package com.msb.jsonboot.core.aop.factory;

import com.msb.jsonboot.core.aop.cglib.CglibAopProxyBeanPostProcessor;
import com.msb.jsonboot.core.aop.jdk.JdkAopProxyBeanPostProcessor;
import com.msb.jsonboot.core.aop.process.BeanPostProcessor;

/**
 * AOP 选择代理方法
 * 
 * @author Stickerleee
 * @since 2021年4月22日 上午10:12:47
 */
public class BeanPostProcessorFactory {

	/**
	 * 根据bean的接口数目选择代理方法
	 * 
	 * @param bean 目标bean
	 * @return 对应代理实例
	 */
	public static BeanPostProcessor getBeanPostProcessor(Object bean) {
        if (bean.getClass().getInterfaces().length> 0){
            return new JdkAopProxyBeanPostProcessor();
        } else {
            return new CglibAopProxyBeanPostProcessor();
        }
	}

}
