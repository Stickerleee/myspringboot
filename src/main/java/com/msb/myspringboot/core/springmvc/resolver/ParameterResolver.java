/**
 * 
 */
package com.msb.myspringboot.core.springmvc.resolver;

import java.lang.reflect.Parameter;

import com.msb.myspringboot.entity.MethodDetail;

/**
 * 解析注解接口
 * 
 * @author Stickerleee
 * @since 2021年4月16日 下午3:22:33
 */
public interface ParameterResolver {

	/**
	 * 调用注解的接口
	 *
	 * @param methodDetail 调用方法的methodDetail
	 * @param parameter    使用的参数
	 * @return 处理后的结果
	 */
	Object resolve(MethodDetail methodDetail, Parameter parameter);
}
