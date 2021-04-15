package com.msb.jsonboot.annotation;

import java.lang.annotation.*;

/**
 * 处理接收GET请求
 * 
 * @author Stickerleee
 * @since 2021年4月15日 上午10:09:11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface GetMapping {

    String value() default "";

}
