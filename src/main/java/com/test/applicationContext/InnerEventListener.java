package com.test.applicationContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 内部事件
 *
 * @Author 46102
 * @Project cloudplatform
 * @Title com.dahua.big.traffic.video.gateway.listener
 * @Date 2019/12/17
 */

@Component
@Slf4j
public class InnerEventListener {


    @EventListener
    public void handleInnerEvent(ApplicationEvent event) {

        if (event instanceof ReportDeviceEvent) {
            log.info("触发设备信息上报 [{}] --------------------", event.getSource());
            if ("deviceStatus".equals(event.getSource())) {
                //设备状态上报
            } else {
                //设备点位上报 或者 设备删除上报
            }
        }
    }
}
