package com.msb.myspringboot.annotation.springmvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 处理接收GET请求
 * 
 * @author Stickerleee
 * @since 2021年4月15日 上午10:09:11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
public @interface GetMapping {

	String value() default "";

}
