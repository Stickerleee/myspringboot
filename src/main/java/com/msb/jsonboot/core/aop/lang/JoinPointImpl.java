/**
 * 
 */
package com.msb.jsonboot.core.aop.lang;

import java.util.Objects;

/**
 * AOP 连接点实现类
 * @author Stickerleee
 * @since 2021年4月21日 下午3:15:35
 */
public class JoinPointImpl implements JoinPoint {

	private final Object aspectBean;

    private final Object target;

    private Object[] args;

    public JoinPointImpl(Object aspectBean, Object target, Object[] args){
        this.aspectBean = aspectBean;
        this.target = target;
        this.args = args;
    }

    @Override
    public Object getAspectBean() {
        return this.aspectBean;
    }

    @Override
    public Object getTarget() {
        return this.target;
    }

    @Override
    public Object[] getArgs() {
        if (Objects.isNull(args)){
            args = new Object[0];
        }
        Object[] array = new Object[args.length];
        System.arraycopy(args, 0, array, 0, args.length);
        return array;
    }
}
