package com.msb.myspringboot.annotation.springmvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 仿Controller注释
 * 
 * @author Stickerleee
 * @since 2021年4月15日 上午9:51:58
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RestController {

	String value() default "";

}
