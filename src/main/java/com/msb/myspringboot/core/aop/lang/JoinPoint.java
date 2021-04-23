/**
 * 
 */
package com.msb.myspringboot.core.aop.lang;

/**
 * AOP 连接点
 * 
 * @author Stickerleee
 * @since 2021年4月21日 下午3:11:31
 */
public interface JoinPoint {

	/**
	 * 获取切面的bean
	 *
	 * @return 切面的bean
	 */
	Object getAspectBean();

	/**
	 * 获取执行目标对象
	 *
	 * @return 执行对象
	 */
	Object getTarget();

	/**
	 * 获取执行的参数
	 *
	 * @return 执行参数
	 */
	Object[] getArgs();
}