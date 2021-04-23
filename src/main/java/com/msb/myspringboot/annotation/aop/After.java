package com.msb.myspringboot.annotation.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AOP 后置增强
 * 
 * @author Stickerleee
 * @since 2021年4月21日 下午2:30:13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface After {

	String value() default "";

}
