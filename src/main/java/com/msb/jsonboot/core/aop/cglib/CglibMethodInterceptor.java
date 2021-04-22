/**
 * 
 */
package com.msb.jsonboot.core.aop.cglib;

import java.lang.reflect.Method;

import com.msb.jsonboot.core.aop.intercept.Interceptor;
import com.msb.jsonboot.entity.MethodInvocation;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author Stickerleee
 * @since 2021年4月22日 下午3:58:47
 */
public class CglibMethodInterceptor implements MethodInterceptor {

	/**
     * 目标对象
     */
    private final Object target;

    /**
     * 定义的拦截器
     */
    private final Interceptor interceptor;

    private CglibMethodInterceptor (Object target, Interceptor interceptor){
        this.target = target;
        this.interceptor = interceptor;
    }

    /**
     * 包装生成代理类
     *
     * @param target 目标对象
     * @param interceptor 执行的拦截器
     * @return 代理类
     */
    public static Object wrap(Object target, Interceptor interceptor){
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(target.getClass().getClassLoader());
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibMethodInterceptor(target, interceptor));
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, objects);
        return interceptor.intercept(methodInvocation);
    }

}
