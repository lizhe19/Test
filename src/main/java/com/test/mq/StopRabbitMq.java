package com.test.mq;

import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lz-119612
 * @version 1.0
 * @date 2020/9/16 14:51
 *  
 **/
public class StopRabbitMq {
    @Autowired
    RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry; // 拿到springboot纳管的所有的rabbitmq的消费者
    public volatile boolean isMaster = false;

    /**
     * 切换为主机的操作
     */
    public void switchMaster() {
        // 如果是master的话主机的mq直接进行开启消费
        try {
            if (!rabbitListenerEndpointRegistry.isRunning()) {
                rabbitListenerEndpointRegistry.start();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 切换为备机的操作
     */
    public void switchSlave() {
        // 如果之前的主机将为备机的话前主机的mq直接暂停消费
        try {
            rabbitListenerEndpointRegistry.stop();
        } catch (Exception e) {
        }
    }
}
