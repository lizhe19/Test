package com.test.applicationContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xing_zheng 118833
 * @version 1.0
 * @date 2019/9/19 11:46
 */
@Component
@Order(99)
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public SpringContextUtil(ApplicationContext applicationContext){
        SpringContextUtil.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static <T> Map<String, T> getBeans(Class name) throws BeansException {
        return applicationContext.getBeansOfType(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }


    public static Environment getEnvironment(){
        return applicationContext.getEnvironment();
    }

    public static void publishEvent(ApplicationEvent event){
        applicationContext.publishEvent(event);
    }
}
