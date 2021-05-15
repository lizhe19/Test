package com.test.nacos;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * nacos作为配置中心，读配置时的两种情形：
 *          1.如果配置格式是YAML或者Properties这种key-value形式的 直接用@Value注解即可获取到最新的配置，更新也能获取到最新值，比较简单，
 *            如果本地配置有相同的配置，则nacos配置优先于本地配置
 *          2.如果配置格式是JSON格式的，则需要使用下面Demo这种设置监听的方式
 *
 * 参考博客：spring cloud获取注册中心所有服务以及服务下的实例信息：https://www.cnblogs.com/hfultrastrong/p/8608903.html
 *          使用nacos作为配置中心：https://www.cnblogs.com/larscheng/p/11392466.html
 *
 * maven依赖:<dependency>
 *               <groupId>com.alibaba.cloud</groupId>
 *               <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
 *           </dependency>
 *           <dependency>
 *              <groupId>com.alibaba.cloud</groupId>
 *              <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
 *           </dependency>
 *
 * @author lz-185979
 * @version 1.0
 * @date 2021/5/13 14:32
 **/
public class NacosTest {

    //nacos如果是读JSON格式的配置 需要使用下面这种方式读配置

    /*@Bean
    public ConfigService configService() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", "172.21.53.55:8848");
        properties.put("namespace", "694de2ac-3931-48bb-966a-7a74f83a34aa");
        return NacosFactory.createConfigService(properties);
    }

    @Autowired
    private ConfigService configService;

    @Override
    public void afterPropertiesSet() throws Exception {
        //链接并获取当前配置
        String configInfo = configService.getConfig("tos-datastoreserver-config.properties", "DEFAULT_GROUP", 3000L);
        System.out.println("[afterPropertiesSet]---------------------------------------------------"+ configInfo);

        //设置监听
        configService.addListener("tos-datastoreserver-config.properties", "DEFAULT_GROUP", new Listener() {
            //null就是使用默认的线程池
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("[receiveConfigInfo]---------------------------------------------------"+ configInfo);
            }
        });

    }*/


    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 获取注册中心实例
     */
    @PostConstruct
    public void serviceUrl() {
        Map<String, List<ServiceInstance>> msl = new HashMap<>();
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> sis = discoveryClient.getInstances(service);
            msl.put(service, sis);
        }
        System.out.println("===============================");
        System.out.println(JSON.toJSONString(msl));
        System.out.println("===============================");

    }
}
