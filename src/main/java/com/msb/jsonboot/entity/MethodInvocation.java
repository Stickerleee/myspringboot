/**
 * 
 */
package com.msb.jsonboot.entity;

import java.lang.reflect.Method;

import com.msb.jsonboot.utils.ReflectionUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Stickerleee
 * @since 2021年4月21日 下午3:19:56
 */
@AllArgsConstructor
@Getter
@ToString
public class MethodInvocation {
	/**
     * 执行的目标对象
     */
    private final Object targetObject;

    /**
     * 方法执行的目标方法
     */
    private final Method targetMethod;

    /**
     * 方法方法执行的参数
     */
    private final Object[] args;

    /**
     * 执行方法
     *
     * @return 方法执行后的返回值
     */
    public Object proceed(){
        return ReflectionUtil.executeTargetMethod(targetObject, targetMethod, args);
    }
}
