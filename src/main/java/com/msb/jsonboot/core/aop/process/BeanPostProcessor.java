/**
 * 
 */
package com.msb.jsonboot.core.aop.process;

/**
 * @author Stickerleee
 * @since 2021年4月21日 下午4:17:03
 */
public interface BeanPostProcessor {

    /**
     * 初始化的后置处理器，在这个地方执行了aop代理
     *
     * @param bean 执行初始化的目标bean
     * @return 后置处理后的对象
     */
    Object postProcessAfterInitialization(Object bean);

}
