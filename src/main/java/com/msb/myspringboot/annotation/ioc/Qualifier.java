package com.msb.myspringboot.annotation.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实现Qualifier注解，在@Autowired接口时选择不同的实现类名称
 * 
 * @author Stickerleee
 * @since 2021年4月19日 下午3:02:51
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface Qualifier {

	String value() default "";

}
