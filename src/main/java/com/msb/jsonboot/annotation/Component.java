/**
 * 
 */
package com.msb.jsonboot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Component标注
 * 将标记的类添加至ioc容器
 * 
 * @author Stickerleee
 * @since 2021年4月17日 上午11:07:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Component {

    String value() default "";

}
