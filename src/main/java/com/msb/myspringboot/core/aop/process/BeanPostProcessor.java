/**
 * 
 */
package com.msb.myspringboot.core.aop.process;

/**
 * @author Stickerleee
 * @since 2021年4月21日 下午4:17:03
 */
public interface BeanPostProcessor {

	/**
	 * 根据Aspect，对Bean进行代理的封装
	 *
	 * @param bean 执行初始化的目标bean
	 * @return 代理包装的Bean
	 */
	Object postProcessAfterInitialization(Object bean);

}
