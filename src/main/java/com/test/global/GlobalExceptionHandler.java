package com.test.global;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * @author xing_zheng
 * @date 2019/9/5 10:50
 * Version 1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*@ResponseBody
    @ExceptionHandler(value = GlobalException.class)
    public JSONObject globalExceptionHandler(GlobalException e) {
        log.error(e.getMsg(), e.getRealException() == null ? e : e.getRealException());
        JSONObject result = new JSONObject();
        result.put("code", e.getCode());
        result.put("msg", e.getMsg());
//        if(e.isChangeHttpState()){
//            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            HttpServletResponse response = requestAttributes.getResponse();
//            response.setStatus(e.getCode());
//        }
        return result;
    }*/



    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JSONObject methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors().stream().map(objError ->
            objError.getDefaultMessage() ).collect(Collectors.joining(","));
        JSONObject result = new JSONObject();
        result.put("code",HttpStatus.BAD_REQUEST.value());
        result.put("msg",msg);
        return result;
    }



    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JSONObject exceptionHandler(Exception e) {
        log.error("", e);
        JSONObject result = new JSONObject();
        result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        if (e instanceof HttpRequestMethodNotSupportedException) {
            result.put("code", HttpStatus.METHOD_NOT_ALLOWED.value());
            result.put("msg", "方法不支持");
        } else if (e instanceof HttpMessageNotReadableException) {
            result.put("code", HttpStatus.BAD_REQUEST.value());
            result.put("msg", "参数错误");
        } else {
            result.put("msg", "程序内部错误");
        }
        return result;
    }
}
