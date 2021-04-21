package com.msb.jsonboot.annotation.springmvc;

import java.lang.annotation.*;

/**
 * 标注传入参数
 * 
 * @author Stickerleee
 * @since 2021年4月15日 上午10:10:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface RequestParam {

    String value();

}
