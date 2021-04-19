package com.msb.jsonboot.annotation;

import java.lang.annotation.*;

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
