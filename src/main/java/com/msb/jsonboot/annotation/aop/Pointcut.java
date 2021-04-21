package com.msb.jsonboot.annotation.aop;

import java.lang.annotation.*;

/**
 * AOP 切点
 * 
 * @author Stickerleee
 * @since 2021年4月21日 下午2:31:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Pointcut {

    String value() default "";

}
