package com.msb.jsonboot.handler.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

import com.msb.jsonboot.core.scanners.AnnotatedClassScanner;
import com.msb.jsonboot.handler.RequestHandler;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理GET请求
 * @author Administrator
 *
 */
@Slf4j
public class GetRequestHandler implements RequestHandler {

	@Override
	public Object handler(FullHttpRequest fullHttpRequest) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(fullHttpRequest.uri(), Charsets.toCharset(CharEncoding.UTF_8));
        Map<String, List<String>> parameters = queryDecoder.parameters();
        //暂时打印参数 先完成netty再处理后续代码
        for (Map.Entry<String, List<String>> parameter : parameters.entrySet()){
            log.info("GET Request {} = {}", parameter.getKey(), parameter.getValue());
        }

        return null;
	}

}
