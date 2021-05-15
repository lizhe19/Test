package com.test.config;

//import feign.RequestInterceptor;
//import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 处理Feign调用其他系统的时候，往请求头里面加上 token这个参数
 */
/*@Configuration
public class FeginInterceptor implements RequestInterceptor {

    @Value("${service.token.header}")
    public String TOKEN_HEADER;

    @Value("${service.token.value}")
    public String TOKEN_VALUE;

    @Value("${service.token.enable}")
    public boolean enable;

    @Override
    public void apply(RequestTemplate template) {
        if(enable){
            template.header(TOKEN_HEADER, TOKEN_VALUE);
        }
    }
}*/
