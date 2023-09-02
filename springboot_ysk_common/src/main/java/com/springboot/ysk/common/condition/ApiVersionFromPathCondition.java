package com.springboot.ysk.common.condition;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description 基于 path 的版本控制
 * @createDate 2023/9/2 20:34
 * @since 1.0.0
 */
public class ApiVersionFromPathCondition implements RequestCondition<ApiVersionFromPathCondition> {
    private static final Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+\\.\\d+)");

    private final String version;

    public ApiVersionFromPathCondition(String version) {
        this.version = version;
    }

    @Override
    public ApiVersionFromPathCondition combine(ApiVersionFromPathCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVersionFromPathCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionFromPathCondition getMatchingCondition(HttpServletRequest request) {
        Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
        if (m.find()) {
            String pathVersion = m.group(1);
            // 这个方法是精确匹配
            if (Objects.equals(pathVersion, version)) {
                return this;
            }
            // 该方法是只要大于等于最低接口version即匹配成功，需要和compareTo()配合
            // 举例：定义有1.0/1.1接口，访问1.2，则实际访问的是1.1，如果从小开始那么排序反转即可
            if (Float.parseFloat(pathVersion) >= Float.parseFloat(version)) {
                return this;
            }

        }
        return null;
    }


    @Override
    public int compareTo(ApiVersionFromPathCondition other, HttpServletRequest request) {
        return 0;
        // 优先匹配最新的版本号，和getMatchingCondition注释掉的代码同步使用
        // return other.getApiVersion().compareTo(this.version);
    }


    public String getApiVersion() {
        return version;
    }
}
