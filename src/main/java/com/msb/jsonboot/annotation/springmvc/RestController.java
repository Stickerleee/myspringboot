package com.msb.jsonboot.annotation.springmvc;

import java.lang.annotation.*;

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
