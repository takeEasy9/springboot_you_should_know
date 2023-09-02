package com.springboot.ysk.common.utils;

import com.springboot.ysk.common.enums.NameValueEnum;
import com.springboot.ysk.common.enums.ValueEnum;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description 枚举工具类
 * @createDate 2023/7/24 22:37
 * @since 1.0.0
 */
public class EnumUtil {

    private EnumUtil() {
    }

    /**
     * 判断枚举值是否存在于指定枚举数组中
     *
     * @param enums 枚举数组
     * @param value 枚举值
     * @param <T>   枚举值类型
     * @return True-枚举值存在 False-枚举值不存在
     */
    public static <T> boolean isExist(ValueEnum<T>[] enums, T value) {
        if (CollectionUtil.isEmpty(enums) || null == value){
            return false;
        }
        for (ValueEnum<T> e : enums) {
            if (value.equals(e.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断枚举值是否不存在于指定枚举数组中
     *
     * @param enums 枚举数组
     * @param value 枚举值
     * @param <T>   枚举值类型
     * @return True-枚举值不存在 False-枚举值存在
     */
    public static <T> boolean isNotExist(ValueEnum<T>[] enums, T value) {
        return !isExist(enums, value);
    }


    /**
     * 判断枚举值是否存与指定枚举类中
     *
     * @param enumClass 枚举类
     * @param value     枚举值
     * @param <E>       枚举类型
     * @param <T>       枚举值类型
     * @return True-枚举值存在 False-枚举值不存在
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<? extends ValueEnum<T>>, T> boolean isExist(Class<E> enumClass, T value) {
        if (null == enumClass || null == value) {
            return false;
        }
        for (Enum<? extends ValueEnum<T>> e : enumClass.getEnumConstants()) {
            if (((ValueEnum<T>) e).getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断枚举值是否不存与指定枚举类中
     *
     * @param enumClass 枚举类
     * @param value     枚举值
     * @param <E>       枚举类型
     * @param <T>       枚举值类型
     * @return True-枚举值不存在 False-枚举值存在
     */
    public static <E extends Enum<? extends ValueEnum<T>>, T> boolean isNotExist(Class<E> enumClass, T value) {
        return !isExist(enumClass, value);
    }

    /**
     * 根据枚举值获取其对应的名字
     *
     * @param enums 枚举列表
     * @param value 枚举值
     * @param <T>   枚举值类型
     * @return 枚举名称
     */
    public static <T> String getNameByValue(NameValueEnum<T>[] enums, T value) {
        if (value == null) {
            return null;
        }
        for (NameValueEnum<T> e : enums) {
            if (value.equals(e.getValue())) {
                return e.getName();
            }
        }
        return null;
    }

    /**
     * 根据枚举名称获取对应的枚举值
     *
     * @param enums 枚举列表
     * @param name  枚举名称
     * @param <T>   枚举值类型
     * @return 枚举值
     */
    public static <T> T getValueByName(NameValueEnum<T>[] enums, String name) {
        if (StringUtil.isEmpty(name)) {
            return null;
        }
        for (NameValueEnum<T> e : enums) {
            if (name.equals(e.getName())) {
                return e.getValue();
            }
        }
        return null;
    }

    /**
     * 根据枚举值获取对应的枚举对象
     *
     * @param enums 枚举列表
     * @param value 枚举值
     * @param <E>   枚举对象类型
     * @param <T>   枚举值类型
     * @return 枚举对象
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<? extends ValueEnum<T>>, T> E getEnumByValue(E[] enums, T value) {
        if (CollectionUtil.isEmpty(enums)) {
            return null;
        }
        if (null == value) {
            return null;
        }
        for (E e : enums) {
            if (((ValueEnum<T>) e).getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据枚举值获取对应的枚举对象
     *
     * @param enumClass 枚举class
     * @return 枚举对象
     */
    public static <E extends Enum<? extends ValueEnum<T>>, T> E getEnumByValue(Class<E> enumClass, T value) {
        return null == enumClass ? null : getEnumByValue(enumClass.getEnumConstants(), value);
    }
}
