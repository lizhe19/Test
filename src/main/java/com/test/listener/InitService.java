package com.test.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 项目启动初始化
 *
 * @Author 46102
 * @Project serviceA
 * @Title com.dahuatech.videoservices.listener
 * @Date 2020/4/15
 */
@Component
@Order(value = 3)
@Slf4j
public class InitService implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) {
        log.info("---------服务启动---------");


    }

}
