/**
 * 
 */
package com.msb.myspringboot.annotation.springmvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * requestBody注解，适用于json格式的post请求
 * 
 * @author Stickerleee
 * @since 2021年4月16日 下午2:54:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface RequestBody {
}
