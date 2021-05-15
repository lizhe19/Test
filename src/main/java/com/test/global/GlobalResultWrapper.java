package com.test.global;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author xing_zheng
 * @date 2019/9/5 10:50
 * Version 1.0
 */
@RestControllerAdvice(basePackages = "com.dahua.big.traffic.cloud.platform.controller")
public class GlobalResultWrapper implements ResponseBodyAdvice {

    private static final Class[] annos = {RequestMapping.class, GetMapping.class,
            PostMapping.class, DeleteMapping.class, PutMapping.class};

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        AnnotatedElement element = returnType.getAnnotatedElement();
        return Arrays.stream(annos).anyMatch(anno -> anno.isAnnotation() && element.isAnnotationPresent(anno));
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        JSONObject msg = new JSONObject();
        msg.put("code", HttpStatus.OK.value());
        msg.put("msg", "Constant.SUCCESS_MSG");
        if (null == body) {
            return msg;
        }
        if (body instanceof Map) {
            msg.putAll((Map) body);
        } else if (body instanceof String) {
            return body;
        } else if (body instanceof List) {
            msg.put("data", body);
        } else {
            Map map = (Map) JSON.toJSON(body);
            msg.putAll(map);
        }
        return msg;
    }
}
