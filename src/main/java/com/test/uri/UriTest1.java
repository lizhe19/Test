package com.test.uri;

import com.alibaba.fastjson.JSON;
import com.sun.jndi.toolkit.url.Uri;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

/**
 * url测试
 *
 * @author lz-119612
 * @version 1.0
 * @date 2020/4/14 10:42
 *  
 **/
public class UriTest1 {
    public static void main(String[] args) throws MalformedURLException {
        String url = "http://10.80.50.221:8081/transcode/service/login";
        Uri uri = new Uri(url);
        System.out.println(JSON.toJSONString(uri));

        //print: {"host":"10.80.50.221","path":"/transcode/service/login","port":8081,"scheme":"http"}
    }
}
