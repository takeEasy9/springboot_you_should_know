package com.springboot.ysk.web.rest;

import com.springboot.ysk.common.annotation.ExcludeResponseBodyAdvice;
import com.springboot.ysk.common.entity.User;
import com.springboot.ysk.common.enums.GuiCodeMsg;
import com.springboot.ysk.common.utils.ConstantUtil;
import com.springboot.ysk.common.utils.ResponseUtil;
import com.springboot.ysk.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description Spring Web 参数校验 Demo
 * @createDate 2023/8/24 1:48
 * @since 1.0.0
 */
@RestController
@RequestMapping(ConstantUtil.API_PREFIX)
public class SpringWebValidationDemoResource {

    private final Logger logger = LoggerFactory.getLogger(SpringWebValidationDemoResource.class);


    /**
     * 最原始的接口参数校验方式, 即手动在 Java 的 Controller或Service 层进行数据校验判断
     * 太繁琐了，光校验代码就会有很多, 不推荐使用该方式
     *
     * @param user          User
     * @param bindingResult BindingResult
     * @return Mono<Map < String, Object>>
     */
    @ExcludeResponseBodyAdvice
    @PostMapping("/validation/demo/manual")
    public Map<String, Object> manualValidation(@RequestBody @Validated User user, BindingResult bindingResult) {
        logger.info("REST: 参数校验方式: 通过判断BindingResult错误信息是否为空, 来判断参数校验失败是否失败");
        if (!checkUserValid(user)) {
            return ResponseUtil.failed(GuiCodeMsg.ACCESS_PARAM_INVALID);
        } else {
            return ResponseUtil.success(GuiCodeMsg.GUI_SUCCESS, "参数校验通过");
        }

    }
    // 手动进行数据校验判断
    private boolean checkUserValid(User user) {
        if (StringUtil.isEmpty(user.getAccount())) {
            logger.error("API参数无效, 用户信息中<用户账号>为空");
            return false;
        }

        if (StringUtil.isEmpty(user.getPassword())) {
            logger.error("API参数无效, 用户信息中<用户账号>为空");
            return false;
        }
        return true;
    }

    /**
     * 普通参数校验demo, 通过判断BindingResult错误信息是否为空, 来判断参数校验失败是否失败
     * 缺点是如果每个接口都使用这种方式，将出现大量重复逻辑代码
     *
     * @param user          User
     * @param bindingResult BindingResult
     * @return Mono<Map < String, Object>>
     */
    @ExcludeResponseBodyAdvice
    @PostMapping("/validation/demo/usual")
    public Map<String, Object> usualAutoValidation(@RequestBody @Validated User user, BindingResult bindingResult) {
        logger.info("REST: 参数校验方式: 通过判断BindingResult错误信息是否为空, 来判断参数校验失败是否失败");
        // 如果有参数校验失败，会将错误信息封装成对象组装在BindingResult里
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (!allErrors.isEmpty()) {
            String errorMsg = allErrors.stream()
                    .map(ObjectError::getDefaultMessage)
                    .filter(StringUtil::isNotEmpty).toList().toString();
            return ResponseUtil.failed(GuiCodeMsg.ACCESS_PARAM_INVALID.getValue(), errorMsg);
        } else {
            return ResponseUtil.success(GuiCodeMsg.GUI_SUCCESS, "参数校验通过");
        }
    }

    /**
     * 推荐使用参数校验失败抛出异常+全局异常处理方式来对参数进行进行校验
     *
     * @param user User
     * @return Mono<Map < String, Object>>
     */
    @ExcludeResponseBodyAdvice
    @PostMapping("/validation/demo/recommend")
    public Map<String, Object> recommendAutoValidation(@RequestBody @Validated User user) {
        logger.info("REST: 参数校验方式: 参数校验失败抛出异常+全局异常处理");
        return ResponseUtil.success(GuiCodeMsg.GUI_SUCCESS, "参数校验通过");
    }

}
