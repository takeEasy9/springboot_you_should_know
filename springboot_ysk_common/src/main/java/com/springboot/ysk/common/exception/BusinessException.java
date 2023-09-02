package com.springboot.ysk.common.exception;

import com.springboot.ysk.common.enums.NameValueEnum;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description 自定义业务异常
 * @createDate 2023/7/25 21:08
 * @since 1.0.0
 */
public class BusinessException  extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 4015687047944726552L;

    /**
     * 消息编码
     */
    private final String code;

    /**
     * 消息编码含义
     */
    private final String msg;

    public BusinessException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(NameValueEnum<String> codeMsg) {
        this.code = codeMsg.getValue();
        this.msg = codeMsg.getName();
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
