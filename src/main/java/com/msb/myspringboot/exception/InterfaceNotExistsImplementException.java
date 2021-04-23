package com.msb.myspringboot.exception;

/**
 * 接口无实现类异常
 * 
 * @author Stickerleee
 * @since 2021年4月19日 下午3:00:00
 */
@SuppressWarnings("serial")
public class InterfaceNotExistsImplementException extends RuntimeException {

	public InterfaceNotExistsImplementException(String message) {
		super(message);
	}

}
