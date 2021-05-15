package com.test.applicationContext;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件
 *
 * @Author 46102
 * @Project videogateway
 * @Title com.dahua.big.traffic.video.gateway.event
 * @Date 2019/12/17
 */
public class ReportDeviceEvent extends ApplicationEvent {


    public ReportDeviceEvent(Object source) {
        super(source);
    }
}
