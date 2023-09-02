package com.springboot.ysk.common.condition;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import java.util.Objects;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description 基于 header 的版本控制
 * @createDate 2023/9/2 20:39
 * @since 1.0.0
 */
public class ApiVersionFromHeaderCondition implements RequestCondition<ApiVersionFromHeaderCondition> {
    private static final String X_VERSION = "X-VERSION";
    private final String version;

    public ApiVersionFromHeaderCondition(String version) {
        this.version = version;
    }

    @Override
    public ApiVersionFromHeaderCondition combine(ApiVersionFromHeaderCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVersionFromHeaderCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionFromHeaderCondition getMatchingCondition(HttpServletRequest httpServletRequest) {
        String headerVersion = httpServletRequest.getHeader(X_VERSION);
        if (Objects.equals(version, headerVersion)) {
            return this;
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionFromHeaderCondition apiVersionCondition, HttpServletRequest httpServletRequest) {
        return 0;
    }

    public String getApiVersion() {
        return version;
    }

}
