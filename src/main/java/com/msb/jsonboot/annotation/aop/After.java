package com.msb.jsonboot.annotation.aop;

import java.lang.annotation.*;

/**
 * AOP 后置增强
 * @author Stickerleee
 * @since 2021年4月21日 下午2:30:13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface After {

    String value() default "";

}
