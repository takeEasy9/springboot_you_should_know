package com.springboot.ysk.common.utils;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description 常量定义
 * @createDate 2023/7/24 22:41
 * @since 1.0.0
 */
public class ConstantUtil {

    private ConstantUtil() {
    }

    /**
     * ********************************  常用时间格式处理  *************************************
     */
    public static final String DATE_TIME_FORMAT_GENERAL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_FULL_NORMAL_WITHOUT_HYPHEN = "yyyyMMdd HH:mm:ss";
    public static final String DATE_FORMAT_GENERAL = "yyyy-MM-dd";
    public static final String TIME_FORMAT_GENERAL = "HH:mm:ss";
    public static final String SPECIAL_CHARACTER_INSTANT_T = "T";
    public static final String SPECIAL_CHARACTER_INSTANT_Z = "Z";
    // 接口url固定前缀
    public static final String API_PREFIX = "springboot-ysk/api/v1";
    // 空字符串
    public static final String STRING_EMPTY = "";
}
