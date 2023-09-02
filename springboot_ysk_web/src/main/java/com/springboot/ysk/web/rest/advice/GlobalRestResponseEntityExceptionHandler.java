package com.springboot.ysk.web.rest.advice;

import com.springboot.ysk.common.enums.GuiCodeMsg;
import com.springboot.ysk.common.enums.SystemEnumEntity;
import com.springboot.ysk.common.utils.ResponseUtil;
import com.springboot.ysk.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description 通过继承ResponseEntityExceptionHandler进行全局异常处理, 两种方式人选一种
 * @createDate 2023/9/2 14:36
 * @since 1.0.0
 */
//@RestControllerAdvice
public class GlobalRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalRestResponseEntityExceptionHandler.class);

    /**
     * 当前运行环境
     */
    private final String activeProfile;

    public GlobalRestResponseEntityExceptionHandler(@Value("${spring.profiles.active:Unknown}") String activeProfile) {
        this.activeProfile = activeProfile;
    }
    // 通过重写对应异常的处理方法，来实现与使用@ExceptionHandler注解类似的效果
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error("<全局异常处理>:接口参数校验异常-MethodArgumentNotValidException：", ex);
        Object body;
        // 生产环境只返回入参无效这一信息
        if (SystemEnumEntity.Profile.PRODUCT.getValue().equals(activeProfile)) {
            body = ResponseUtil.failed(GuiCodeMsg.ACCESS_PARAM_INVALID);
        } else {
            // 从异常对象中拿到ObjectError对象, 并从ObjectError对象获取校验失败提示信息
            var errors = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).filter(StringUtil::isNotEmpty).toList();
            // 非生产环境提取错误提示信息进行返回
            body = ResponseUtil.failed(GuiCodeMsg.ACCESS_PARAM_INVALID.getValue(), errors.toString());
        }
        return handleExceptionInternal(ex, body, headers, status, request);
    }

}
