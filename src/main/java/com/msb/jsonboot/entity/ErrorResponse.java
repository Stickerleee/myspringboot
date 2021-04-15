/**
 * 
 */
package com.msb.jsonboot.entity;

import com.msb.jsonboot.utils.DateUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 服务器处理异常时，返回的数据格式
 * 
 * @author Stickerleee
 * @since 2021年4月15日 下午4:54:44
 */
@Getter
@ToString
@NoArgsConstructor
public class ErrorResponse {

    /**
     * 状态码
     */
    private int status;

    /**
     * 错误原因
     */
    private String message;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 时间
     */
    private String dateTime;


    public ErrorResponse(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.dateTime = DateUtil.now();
    }
}