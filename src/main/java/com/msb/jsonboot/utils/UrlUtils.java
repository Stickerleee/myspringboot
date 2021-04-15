/**
 * 
 */
package com.msb.jsonboot.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.handler.codec.http.QueryStringDecoder;

/**
 * 扁平化map数据
 * 
 * @author Stickerleee
 * @since 2021年4月15日 下午3:51:30
 */
public class UrlUtils {
	
	/**
	 * 扁平化参数列表
	 * @param queryDecoder 目标url
	 * @return 参数列表Map
	 */
    public static Map<String, String> getQueryParam(QueryStringDecoder queryDecoder){
        Map<String, List<String>> parameters = queryDecoder.parameters();
        Map<String, String> queryParam = new HashMap<>();
        for(Map.Entry<String, List<String>> parameter : parameters.entrySet()){
            for(String value : parameter.getValue()){
                queryParam.put(parameter.getKey(), value);
            }
        }
        return queryParam;
    }
}
