package com.test.global;

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


@RestControllerAdvice(basePackages = "com.dahuatech.videoservices.controller")
public class GlobalResultWrapper2 implements ResponseBodyAdvice {

    private static final Class[] annos = {RequestMapping.class, GetMapping.class,
            PostMapping.class, DeleteMapping.class, PutMapping.class};

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        AnnotatedElement element = returnType.getAnnotatedElement();
        return Arrays.stream(annos).anyMatch(anno -> anno.isAnnotation() && element.isAnnotationPresent(anno));
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        BaseResponse<Object> baseResponse = new BaseResponse<>();
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setSuccess(Boolean.TRUE);
        if (null == body) {
            return baseResponse;
        }
        baseResponse.setResult(body);
        return baseResponse;
    }
}
