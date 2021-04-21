/**
 * 
 */
package com.msb.jsonboot.core.aop.intercept;

import com.msb.jsonboot.entity.MethodInvocation;

/**
 * 拦截器抽象类
 * 
 * @author Stickerleee
 * @since 2021年4月21日 下午3:18:21
 */
public abstract class Interceptor {
	
	 /**
     * 拦截器优先级顺序
     *
     * @return 顺序值
     */
    public int getOrder(){
        return -1;
    }

    /**
     * 该拦截器是否适配该bean
     *
     * @param object bean
     * @return 是否适配
     */
    public boolean supports(Object object){
        return false;
    }

    /**
     * 方法执行器
     *
     * @param methodInvocation 方法执行器
     * @return 返回数据
     */
    public abstract Object intercept(MethodInvocation methodInvocation);
}
