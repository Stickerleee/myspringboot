package com.msb.jsonboot.annotation.aop;

import java.lang.annotation.*;

/**
 * AOP 切面标识
 * 
 * @author Stickerleee
 * @since 2021年4月21日 下午2:30:45
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Aspect {

    String value() default "";

}
