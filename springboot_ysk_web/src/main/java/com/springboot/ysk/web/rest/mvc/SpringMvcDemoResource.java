package com.springboot.ysk.web.rest.mvc;

import com.springboot.ysk.common.enums.GuiCodeMsg;
import com.springboot.ysk.common.utils.ConstantUtil;
import com.springboot.ysk.common.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description springMVC demo
 * @createDate 2023/7/24 22:17
 * @since 1.0.0
 */
@RestController
@RequestMapping(ConstantUtil.API_PREFIX + "/webmvc/demo")
public class SpringMvcDemoResource {
    private final Logger logger = LoggerFactory.getLogger(SpringMvcDemoResource.class);

    private final WebApplicationContext applicationContext;

    public SpringMvcDemoResource(WebApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @GetMapping("/mvcProcessDemo")
    public Map<String, Object> mvcProcessDemo() {
        logger.info("REST: springMVC HTTP 请求处理过程demo");
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((k, v) -> logger.info("RequestMappingInfo:<{}> HandlerMethod: <{}>", k, v));
        return ResponseUtil.success(GuiCodeMsg.GUI_SUCCESS, "Hello World!");
    }


}
