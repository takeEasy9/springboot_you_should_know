package com.springboot.ysk.common.annotation;

import java.lang.annotation.*;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description
 * @createDate 2023/8/30 22:25
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ExcludeResponseBodyAdvice {
}
