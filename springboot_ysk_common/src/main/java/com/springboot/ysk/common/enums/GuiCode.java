package com.springboot.ysk.common.enums;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description
 * @createDate 2023/7/25 21:31
 * @since 1.0.0
 */
public enum GuiCode implements ValueEnum<String> {
    /**
     * GUI成功
     */
    GUI_SUCCESS("G0000"),
    /**
     * GUI失败
     */
    GUI_FAILED("G0001"),

    // 参数无效
    ACCESS_PARAMETER_INVALID("G0100"),
    ;
    /**
     * 消息编码
     */
    private final String code;

    GuiCode(String value) {
        this.code = value;
    }

    @Override
    public String getValue() {
        return this.code;
    }
}
