package com.test.guava;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.test.Test;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 参考文档：https://www.jianshu.com/p/2469059b204a
 *
 * @author lz-119612
 * @version 1.0
 * @date 2019/12/24 15:21
 **/
public class TypeTokenTest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class BeanOne<T, U> {
        private T sid;
        private U obj;
        private Integer age;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class BeanTwo {
        private Integer msgId;
        private String msgName;
    }


    /**
     * TypeToken
     * @param args
     */
    public static void main(String[] args) {
        BeanOne<String, Object> bean1 = new BeanOne<>();
        bean1.setSid("sidStr");
        bean1.setAge(10);
        bean1.setObj(new BeanTwo(33, "msgName33"));

        BeanOne<Object, Object> bean2 = new BeanOne<>();
        bean2.setSid(1111111);
        bean2.setAge(20);
        bean2.setObj(Lists.newArrayList("aaa", "bbb", "ccc"));

        String str1 = new Gson().toJson(bean1);
        String str2 = JSON.toJSONString(bean2);
        System.out.println("str1=" + str1);
        System.out.println("str2=" + str2);

        //不使用TypeToken可能导致序列号反序列化出现问题或者差错
        BeanOne<String, BeanTwo> beanObj1 = new Gson().fromJson(str1, BeanOne.class);
        BeanOne<Integer, List<String>> beanObj2 = JSONObject.parseObject(str2, BeanOne.class);

        System.out.println("beanObj1=" + JSON.toJSONString(beanObj1));
        System.out.println("beanObj2=" + JSON.toJSONString(beanObj2));

        //使用TypeToken能有效避免这些问题
        BeanOne<String, BeanTwo> beanObj3 = new Gson().fromJson(str1, new TypeToken<BeanOne<String, BeanTwo>>() {}.getType());
        BeanOne<Integer, List<String>> beanObj4 = JSONObject.parseObject(str2, new TypeToken<BeanOne<Integer, List<String>>>() {}.getType());
        System.out.println("beanObj3=" + JSON.toJSONString(beanObj3));
        System.out.println("beanObj4=" + JSON.toJSONString(beanObj4));


        TestInterface<String, String, Boolean>  testInterface = new Test();
        System.out.println(testInterface.test("aaa", "aaa"));
    }


    @Bean
    public TestInterface getTestBean() {
        return new Test();
    }

    interface TestInterface<T, U, R> {

        R test(T t, U u);
    }

    static class Test implements TestInterface<String, String, Boolean> {
        /**
         * test
         */
        @Override
        public Boolean test(String s, String y) {
            return s.equals(y);
        }
    }




}
