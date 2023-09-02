package com.springboot.ysk.common.enums;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description 系统级别消息编码
 * @createDate 2023/7/25 21:27
 * @since 1.0.0
 */
public enum SystemCode implements ValueEnum<String> {

    /**
     * 系统正常
     */
    SYSTEM_SUCCESS("S0000"),
    /**
     * 系统异常
     */
    SYSTEM_FAILED("S0001"),

    /**
     * 接口404异常
     */
    SYSTEM_HTTP_API_RESPONSE_EXCEPTION("S0100"),

    /**
     * 接口404异常
     */
    SYSTEM_HTTP_API_NOT_FOUND("S0102"),

    /**
     * 接口返回json处理异常
     */
    SYSTEM_HTTP_API_JSON_PROCESS_EXCEPTION("S0103"),
    ;

    /**
     * 消息编码
     */
    private final String code;

    SystemCode(String value) {
        this.code = value;
    }
    @Override
    public String getValue() {
        return this.code;
    }
}
