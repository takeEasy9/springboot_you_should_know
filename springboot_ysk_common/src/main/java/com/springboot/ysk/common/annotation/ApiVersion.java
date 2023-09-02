package com.springboot.ysk.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description 接口的版本控制注解
 * @createDate 2023/9/2 20:32
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {
    // // 默认接口版本号1.0开始，这里我只做了两级，多级可在正则进行控制
    String value() default "v1";
}
