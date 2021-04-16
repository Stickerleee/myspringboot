package com.msb.jsonboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 存放方法类型的参数
 * 
 * @author Stickerleee
 * @since 2021年4月16日 下午1:50:50
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MethodDetail {
    /**
     * 请求方法类型
     */
    private Method method;

    /**
     * url参数映射 如 /user/{name} 对应 /user/xx  user:user  name:xx
     */
    private Map<String, String> urlParameterMappings;

    /**
     * 查询参数映射 外部查询 如 name=xx&age=18
     */
    private Map<String, String> queryParameterMappings;
    private String json;
}
