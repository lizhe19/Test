package com.test.annotation;

import lombok.Setter;

/**
 * @author lz-119612
 * @version 1.0
 * @date 2020/12/2 10:24
 *  
 **/
//@ConfigurationProperties(prefix="vip")
@Setter
public class ConfigurationPropertiesTest {
    //使用这个注解 字段必须要有set方法 否则配置文件写不进去

    private Boolean used;
    private String value;
}
