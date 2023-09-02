package com.springboot.ysk.common.enums;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description gui code msg definition
 * @createDate 2023/7/25 21:34
 * @since 1.0.0
 */
public enum GuiCodeMsg implements NameValueEnum<String> {

    /**
     * GUI运行正常
     */
    GUI_SUCCESS(GuiCode.GUI_SUCCESS, "GUI运行正常"),
    /**
     * 用户端运行异常
     */
    GUI_FAILED(GuiCode.GUI_FAILED, "GUI运行异常"),

    // 参数异常
    ACCESS_PARAM_INVALID(GuiCode.ACCESS_PARAMETER_INVALID, "入参无效，请检查"),
    ;


    /**
     * 消息编码
     */
    private final String code;

    /**
     *消息编码含义
     */
    private final String msg;

    GuiCodeMsg(ValueEnum<String> code, String msg) {
        this.code = code.getValue();
        this.msg = msg;
    }


    @Override
    public String getValue() {
        return code;
    }

    @Override
    public String getName() {
        return msg;
    }
}
