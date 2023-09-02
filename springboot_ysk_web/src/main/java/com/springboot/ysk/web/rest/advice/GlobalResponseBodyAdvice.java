package com.springboot.ysk.web.rest.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.ysk.common.annotation.ExcludeResponseBodyAdvice;
import com.springboot.ysk.common.enums.GuiCodeMsg;
import com.springboot.ysk.common.enums.SystemCodeMsg;
import com.springboot.ysk.common.enums.SystemEnumEntity;
import com.springboot.ysk.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description 接口返回统一响应体
 * @createDate 2023/8/30 22:01
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private final Logger logger = LoggerFactory.getLogger(GlobalResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 如果接口返回的类型本身就是Mono那就没有必要进行额外的操作，返回false
        // 如果方法上加了我们的自定义注解也没有必要进行额外的操作
        return !(returnType.getGenericParameterType().equals(Mono.class) || returnType.hasMethodAnnotation(ExcludeResponseBodyAdvice.class));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 判断是否是404等
        if (body instanceof Map<?, ?> bodyMap) {
            var httpStatus = bodyMap.containsKey("status") ? (Integer) bodyMap.get("status") : HttpStatus.OK.value();
            if (httpStatus != HttpStatus.OK.value()) {
                logger.error("HTTP 请求异常:{}", body);
                return body;
            }
        }
        var resultMap = new HashMap<>(4);
        resultMap.put(SystemEnumEntity.ApiRes.CODE.getValue(), GuiCodeMsg.GUI_SUCCESS.getValue());
        resultMap.put(SystemEnumEntity.ApiRes.MESSAGE.getValue(), GuiCodeMsg.GUI_SUCCESS.getName());
        resultMap.put(SystemEnumEntity.ApiRes.DATA.getValue(), body);
        // String类型不能直接包装，所以要进行些特别的处理
        if (String.class.equals(returnType.getGenericParameterType())) {
            var objectMapper = new ObjectMapper();
            // 将数据包装在后，再转换为json字符串响应给前端
            try {
                return objectMapper.writeValueAsString(resultMap);
            } catch (JsonProcessingException e) {
                throw new BusinessException(SystemCodeMsg.SYSTEM_HTTP_API_JSON_PROCESS_EXCEPTION);
            }
        }
        // 将原本的数据包装在resultMap里
        return resultMap;

    }
}
