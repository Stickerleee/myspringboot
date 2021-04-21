/**
 * 
 */
package com.msb.jsonboot.core.springmvc.resolver.factory;

import java.lang.reflect.Parameter;

import com.msb.jsonboot.annotation.springmvc.*;
import com.msb.jsonboot.core.springmvc.resolver.ParameterResolver;
import com.msb.jsonboot.core.springmvc.resolver.impl.DefaultParameterResolver;
import com.msb.jsonboot.core.springmvc.resolver.impl.PathVariableParameterResolver;
import com.msb.jsonboot.core.springmvc.resolver.impl.RequestBodyParameterResolver;
import com.msb.jsonboot.core.springmvc.resolver.impl.RequestParamParameterResolver;

/**
 * 注解解析工厂函数
 * 
 * @author Stickerleee
 * @since 2021年4月16日 下午3:23:48
 */
public class ParameterResolverFactory {
	/**
     * 根据parameter的类型 获取对应的执行方式
     *
     * @param parameter 参数
     * @return 参数执行器
     */
    public static ParameterResolver get(Parameter parameter){

    	//RequestParam
        if (parameter.isAnnotationPresent(RequestParam.class)){
            return new RequestParamParameterResolver();
        }
        //RequestBody
        if (parameter.isAnnotationPresent(RequestBody.class)){
            return new RequestBodyParameterResolver();
        }
        if (parameter.isAnnotationPresent(PathVariable.class)){
            return new PathVariableParameterResolver();
        }
        return new DefaultParameterResolver();
    }
}
