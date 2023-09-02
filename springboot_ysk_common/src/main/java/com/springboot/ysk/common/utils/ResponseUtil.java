package com.springboot.ysk.common.utils;

import com.springboot.ysk.common.enums.NameValueEnum;
import com.springboot.ysk.common.enums.SystemEnumEntity;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description 响应工具类
 * @createDate 2023/8/24 1:06
 * @since 1.0.0
 */
public class ResponseUtil {

    private ResponseUtil() {

    }

    /**
     * 接口正常返回结果封装方法
     *
     * @param codeMsg codeMsg api消息编码与消息枚举值
     * @param data    Object 接口待返回数据
     * @return Mono<Map < String, Object>>
     */
    public static Map<String, Object> success(NameValueEnum<String> codeMsg, Object data) {
        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put(SystemEnumEntity.ApiRes.CODE.getValue(), codeMsg.getValue());
        resultMap.put(SystemEnumEntity.ApiRes.MESSAGE.getValue(), codeMsg.getName());
        resultMap.put(SystemEnumEntity.ApiRes.DATA.getValue(), data);
        return resultMap;
    }

    /**
     * 接口异常返回结果封装方法
     *
     * @param codeMsg api消息编码与消息枚举值
     * @return Mono<Map < String, Object>>
     */
    public static Map<String, Object> failed(NameValueEnum<String> codeMsg) {
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put(SystemEnumEntity.ApiRes.CODE.getValue(), codeMsg.getValue());
        resultMap.put(SystemEnumEntity.ApiRes.MESSAGE.getValue(), codeMsg.getName());
        return resultMap;
    }

    /**
     * 接口异常返回结果封装方法
     *
     * @param code 消息编码
     * @param msg  消息
     * @return Mono<Map < String, Object>>
     */
    public static Map<String, Object> failed(String code, String msg) {
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put(SystemEnumEntity.ApiRes.CODE.getValue(), code);
        resultMap.put(SystemEnumEntity.ApiRes.MESSAGE.getValue(), msg);
        return resultMap;
    }
}
