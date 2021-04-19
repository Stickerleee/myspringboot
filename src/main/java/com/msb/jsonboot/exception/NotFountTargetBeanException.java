package com.msb.jsonboot.exception;

/**
 * dependencyInjection未能找到目标类时进行报错
 * 
 * @author Stickerleee
 * @since 2021年4月19日 下午3:19:56
 */
public class NotFountTargetBeanException extends RuntimeException{

    public NotFountTargetBeanException(String mesage){
        super(mesage);
    }

}

