package com.msb.jsonboot.annotation.aop;

import java.lang.annotation.*;

/**
 * AOP 前置增强
 * 
 * @author Stickerleee
 * @since 2021年4月21日 下午2:31:03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Before {

    String value() default "";

}
