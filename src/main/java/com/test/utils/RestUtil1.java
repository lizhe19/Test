package com.test.utils;

import com.alibaba.fastjson.JSONObject;
import com.test.applicationContext.SpringContextUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

/**
 * *author xing_zheng
 * *@date 2019/10/22
 */

@Slf4j
public class RestUtil1 {

    public static RestTemplate restTemplate;

    public static synchronized void buildTemplate(String readTimeOut) {
        String connectTimeout = SpringContextUtil.getEnvironment().getProperty("connect.timeout");
        if (StringUtils.isEmpty(connectTimeout)) {
            connectTimeout = "2000";
        }
        if (StringUtils.isEmpty(readTimeOut)) {
            readTimeOut = SpringContextUtil.getEnvironment().getProperty("read.timeout");
        }
        if (StringUtils.isEmpty(readTimeOut)) {
            readTimeOut = "30000";
        }
        RestTemplateBuilder builder = SpringContextUtil.getBean(RestTemplateBuilder.class);
        RestTemplate copy = builder.build();
        SimpleHttpRequestFactory requestFactory = new SimpleHttpRequestFactory();
        //借助BufferingClientHttpRequestFactory包装SimpleClientHttpRequestFactory，旨在缓存request和response流，因为restTemplate设置拦截器里
        //如果有读取流的操作 例如这里读取返回值时用到response.getBody方法，导致流关闭
        //解决方法：1.不用SimpleClientHttpRequestFactory  改用HttpComponentsClientHttpRequestFactory 默认带缓存
        //          2.用BufferingClientHttpRequestFactory包装一下
        //          3.动态代理的方式包装代理一下：参见文档https://www.cnblogs.com/pi-laoban/p/14071279.html
        BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory = new BufferingClientHttpRequestFactory(requestFactory);
        requestFactory.setConnectTimeout(Integer.parseInt(connectTimeout));
        requestFactory.setReadTimeout(Integer.parseInt(readTimeOut));
        copy.setRequestFactory(bufferingClientHttpRequestFactory);
        //配置restTemplate拦截器
        copy.setInterceptors(Collections.singletonList((httpRequest, body, clientHttpRequestExecution) -> {
            ClientHttpResponse response = null;
//            UserLogService userLogService = SpringContextUtil.getBean(UserLogService.class);
            try {
                response = clientHttpRequestExecution.execute(httpRequest, body);
                //记录点位上报日志
//                userLogService.addOperationLog(httpRequest, body, response);
            } catch (IOException e) {
                //记录点位上报日志
//                userLogService.addOperationLog(httpRequest, body, response);
                throw e;
            }
            return response;
        }));
        restTemplate = copy;
    }

    public static JSONObject post(String url, HttpHeaders headers, Object params) {
//        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        HttpEntity request = new HttpEntity(params, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);
        return JSONObject.parseObject(result.getBody());
    }

    public static JSONObject put(String url, HttpHeaders headers, Object params) {
//        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        HttpEntity request = new HttpEntity(params, headers);
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        return JSONObject.parseObject(result.getBody());
    }

    private static JSONObject exchange(String url, HttpHeaders headers, Object params, HttpMethod method) {
//        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        HttpEntity request = new HttpEntity(params, headers);
        ResponseEntity<String> result = restTemplate.exchange(url, method, request, String.class);
        return JSONObject.parseObject(result.getBody());
    }

    public static JSONObject get(String url, HttpHeaders headers) {
        return exchange(url, headers, null, HttpMethod.GET);
    }


    public static JSONObject getRemoteData(String url) {
//        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        //log.info("Request URL :" + url + "|");
        JSONObject resp = new JSONObject();
        try {
            URI uri = new URI(url);
            String result = restTemplate.getForObject(uri, String.class);
            resp = JSONObject.parseObject(result);
        } catch (URISyntaxException e) {
            log.error("Create URI Exception !");
        }
        log.info("Response result : " + resp.toJSONString());
        return resp;
    }

    public static ResponseEntity<String> httpGetString(String url, HttpHeaders headers) {
//        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
    }

    public static ResponseEntity httpDeleteString(String url, HttpHeaders headers) {
//        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(null, headers), String.class);
    }

    public static ResponseEntity<String> httpPostString(String url, HttpHeaders headers, Object params) {
//        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        return restTemplate.postForEntity(url, new HttpEntity<>(params, headers), String.class);
    }

    public static ResponseEntity httpPutString(String url, HttpHeaders headers, Object params) {
//        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(params, headers), String.class);
    }

    public static <T> BaseResult post(String url, HttpHeaders headers, Object params, Class<T> type) {
//        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        HttpEntity request = new HttpEntity(params, headers);

        BaseResult<T> baseResult = new BaseResult<T>();
        try {
            ResponseEntity<T> result = restTemplate.exchange(url, HttpMethod.POST, request, type);

            if (HttpStatus.OK.value() == result.getStatusCodeValue()) {

                baseResult.setSuccess(true);
                baseResult.setResult(result.getBody());
                return baseResult;
            }

        } catch (RestClientException e) {
            log.error("post url [{}] error for [{}]", url, e);
        }

        return baseResult;
    }

    public static JSONObject result401() {
        JSONObject result = new JSONObject();
        result.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        result.put("msg", "没有访问权限");
        return result;
    }

    public static JSONObject result403() {
        JSONObject result = new JSONObject();
        result.put("code", HttpServletResponse.SC_FORBIDDEN);
        result.put("msg", "没有访问权限");
        return result;
    }


    @Data
    public static class BaseResult<T> {

        private boolean success = false;

        private T result;

    }
}
