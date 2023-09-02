package com.springboot.ysk.web.rest.advice;

import com.springboot.ysk.common.enums.GuiCodeMsg;
import com.springboot.ysk.common.enums.SystemCodeMsg;
import com.springboot.ysk.common.enums.SystemEnumEntity;
import com.springboot.ysk.common.exception.BusinessException;
import com.springboot.ysk.common.utils.ResponseUtil;
import com.springboot.ysk.common.utils.StringUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description 全局异常处理类, 指定需要处理的异常对象, 设置合适的Http消息码
 * 未解决的的问题：
 * 1. ExceptionHandler方法返回值的问题, 当方法返回 Mono<Map<String,Object>> 时, 接口返回 { "scanAvailable": true }
 * 而非 { "msg": "[用户邮箱不能为空]", "code": "G0100"}, 暂时还不知道是为什么;
 * 2. 捕获404异常, 需要修改配置如下
 * spring:
 *   mvc:
 *     throw-exception-if-no-handler-found: true
 *   web:
 *     resources:
 *       add-mappings: false
 * 3.捕获404异常deng, 也可以继承自 ResponseEntityExceptionHandler handleException方法对 NoHandlerFoundException, HttpMessageNotReadableException, MethodArgumentNotValidException, BindException等异常都做了处理
 * @createDate 2023/8/22 22:38
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalRestControllerExceptionHandlerAdvice {
    private final Logger logger = LoggerFactory.getLogger(GlobalRestControllerExceptionHandlerAdvice.class);

    /**
     * 当前运行环境
     */
    private final String activeProfile;

    public GlobalRestControllerExceptionHandlerAdvice(@Value("${spring.profiles.active:Unknown}") String activeProfile) {
        this.activeProfile = activeProfile;
    }

    /**
     * HTTP消息转换异常处理
     *
     * @param e NoHandlerFoundException
     * @return Map < String, Object>
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        logger.error("<全局异常处理>: API NOT FOUND异常：", e);
        return ResponseUtil.failed(SystemCodeMsg.SYSTEM_HTTP_API_NOT_FOUND);
    }

    /**
     * HTTP消息转换异常处理
     *
     * @param e MethodArgumentNotValidException
     * @return Map < String, Object>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        logger.error("<全局异常处理>:HTTP消息转换异常：", e);
        return ResponseUtil.failed(SystemCodeMsg.SYSTEM_HTTP_API_NOT_FOUND);
    }

    //  参数校验失败，三种使用场景会抛出三种异常或者警告，分别是MethodArgumentNotValidException、ConstraintViolationException、BindException异常
    // 下面分别对这三个异常进行处理
    /**
     * 接口参数校验异常处理
     *
     * @param e MethodArgumentNotValidException
     * @return Map < String, Object>
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> bindExceptionHandler(BindException e) {
        logger.error("<全局异常处理>:接口参数校验异常-MethodArgumentNotValidException | BindException：", e);
        // 生产环境只返回入参无效这一信息
        if (SystemEnumEntity.Profile.PRODUCT.getValue().equals(activeProfile)) {
            return ResponseUtil.failed(GuiCodeMsg.ACCESS_PARAM_INVALID);
        } else {
            // 从异常对象中拿到ObjectError对象, 并从ObjectError对象获取校验失败提示信息
            var errors = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).filter(StringUtil::isNotEmpty).toList();
            // 非生产环境提取错误提示信息进行返回
            return ResponseUtil.failed(GuiCodeMsg.ACCESS_PARAM_INVALID.getValue(), errors.toString());
        }

    }

    /**
     * 接口参数校验异常处理
     *
     * @param e ConstraintViolationException
     * @return Map < String, Object>
     */
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> bindExceptionHandler(ConstraintViolationException e) {
        logger.error("<全局异常处理>:接口参数校验异常-ConstraintViolationException：", e);
        // 生产环境只返回入参无效这一信息
        if (SystemEnumEntity.Profile.PRODUCT.getValue().equals(activeProfile)) {
            return ResponseUtil.failed(GuiCodeMsg.ACCESS_PARAM_INVALID);
        } else {
            // 非生产环境提取错误提示信息进行返回
            var messages = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();
            return ResponseUtil.failed(GuiCodeMsg.ACCESS_PARAM_INVALID.getValue(), messages.toString());
        }

    }

    /**
     * 空指针异常处理
     *
     * @param e Exception
     * @return Mono<Map < String, Object>>
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> exceptionHandler(NullPointerException e) {
        // 打印异常堆栈信息
        logger.error("<全局异常处理>:空指针异常：", e);
        return ResponseUtil.failed(GuiCodeMsg.GUI_FAILED);
    }

    /**
     * 索引范围异常处理
     *
     * @param indexOutOfBoundsException NullPointerException
     * @return Mono<Map < String, Object>>
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException indexOutOfBoundsException) {
        // 打印异常堆栈信息
        logger.error("<全局异常处理>:索引范围异常：", indexOutOfBoundsException);
        return ResponseUtil.failed(GuiCodeMsg.GUI_FAILED);
    }

    /**
     * 业务异常处理
     *
     * @param e BusinessException
     * @return Mono<Map < String, Object>>
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> businessExceptionHandler(BusinessException e) {
        // 打印异常堆栈信息
        logger.error("<全局异常处理>:业务异常：", e);
        return ResponseUtil.failed(GuiCodeMsg.GUI_FAILED);
    }

    /**
     * 其他异常
     *
     * @param e BusinessException
     * @return Mono<Map < String, Object>>
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Order
    public Map<String, Object> exceptionHandler(Exception e) {
        // 打印异常堆栈信息
        logger.error("<全局异常处理>:其他异常：", e);
        return ResponseUtil.failed(GuiCodeMsg.GUI_FAILED);
    }




}
