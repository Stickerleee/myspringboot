package com.msb.jsonboot.annotation.springmvc;

import java.lang.annotation.*;

/**
 * 
 * @author Stickerleee
 * @since 2021年4月16日 上午11:32:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface PathVariable {

    String value();

}
