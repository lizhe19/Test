package com.test.utils;

import com.alibaba.fastjson.JSONObject;
import com.test.applicationContext.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * rest工具类
 *
 * @author lz-119612
 * @version 1.0
 * @date 2020/7/13 15:18
 *  
 **/

@Slf4j
public class RestUtil {

    public static JSONObject post(String url, HttpHeaders headers, Object params) {
        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        HttpEntity request = new HttpEntity(params, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);
        return JSONObject.parseObject(result.getBody());
    }

    public static JSONObject put(String url, HttpHeaders headers, Object params) {
        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        HttpEntity request = new HttpEntity(params, headers);
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        return JSONObject.parseObject(result.getBody());
    }

    private static JSONObject exchange(String url, HttpHeaders headers, Object params, HttpMethod method) {
        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        HttpEntity request = new HttpEntity(params, headers);
        ResponseEntity<String> result = restTemplate.exchange(url, method, request, String.class);
        return JSONObject.parseObject(result.getBody());
    }

    public static JSONObject get(String url, HttpHeaders headers) {
        return exchange(url, headers, null, HttpMethod.GET);
    }


    public static JSONObject getRemoteData(String url) {
        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
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

    public static ResponseEntity httpGetString(String url, HttpHeaders headers) {
        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
    }

    public static ResponseEntity httpDeleteString(String url, HttpHeaders headers) {
        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(null, headers), String.class);
    }

    public static ResponseEntity httpPostString(String url, HttpHeaders headers, Object params) {
        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        return restTemplate.postForEntity(url, new HttpEntity<>(params, headers), String.class);
    }
    public static ResponseEntity httpPutString(String url, HttpHeaders headers, Object params) {
        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(params, headers), String.class);
    }

}
