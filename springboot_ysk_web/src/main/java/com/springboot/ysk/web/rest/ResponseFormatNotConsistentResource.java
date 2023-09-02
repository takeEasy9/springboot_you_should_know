package com.springboot.ysk.web.rest;

import com.springboot.ysk.common.annotation.ExcludeResponseBodyAdvice;
import com.springboot.ysk.common.entity.User;
import com.springboot.ysk.common.utils.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description 接口相应格式不一致 Demo
 * @createDate 2023/8/30 22:44
 * @since 1.0.0
 */
@RestController
@RequestMapping(ConstantUtil.API_PREFIX + "/webmvc/demo")
public class ResponseFormatNotConsistentResource {
    private final Logger logger = LoggerFactory.getLogger(ResponseFormatNotConsistentResource.class);

    @ExcludeResponseBodyAdvice
    @GetMapping("/responseFormatNotConsistent")
    public User getUser() {
        logger.info("REST 接口相应格式不一致 Demo API");
        User user = new User();
        user.setAccount("account");
        user.setEmail("123@Email");
        return user;
    }
}
