package com.test.beancopy;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.util.List;
import java.util.Map;

/**
 * bean拷贝工具
 *              1.BeanCopier   2.spring BeanUtils    3.apache BeanUtils（不推荐使用）   4.hutool BeanUtils
 *
 * @author lz-119612
 * @version 1.0
 * @date 2020/6/22 8:53
 *  
 **/
public class BeanCopyTest {
    private static Map<String, Object> cache = Maps.newConcurrentMap();

    public static void main(String[] args) {
        Do srcDo = new Do(1000L, "name1", 10086, Lists.newArrayList(new DoDetail(1L, "detailName1", 22222)));
        Dto targetDto = new Dto();
        copy(srcDo, targetDto);
        System.out.println(JSON.toJSONString(targetDto));

        Dto targetDto1 = new Dto();
        copy(srcDo, targetDto1);
        System.out.println(JSON.toJSONString(targetDto1));

        /*Dto targetDto2 = new Dto();
        BeanUtils.copyProperties(srcDo, targetDto2);
        System.out.println(JSON.toJSONString(targetDto2));

        Dto targetDto3 = new Dto();
        BeanUtil.copyProperties(srcDo, targetDto3);
        System.out.println(JSON.toJSONString(targetDto3));*/


        //测试converter
        Dto targetDto4 = new Dto();
        BeanCopier beanCopier2 = BeanCopier.create(srcDo.getClass(), targetDto4.getClass(), true);
        TestConverter testConverter = new TestConverter();
        beanCopier2.copy(srcDo, targetDto4, testConverter);
        System.out.println(JSON.toJSONString(targetDto4));
    }

    /**
     * 将实例缓存 不需要每次都构建  提升效率
     * @param srcObj
     * @param targetObj
     */
    private static void copy(Object srcObj, Object targetObj) {
        String key = srcObj.getClass().getName() + targetObj.getClass().getName();
        BeanCopier beanCopier;
        if (null == cache.get(key)) {
            beanCopier = BeanCopier.create(srcObj.getClass(), targetObj.getClass(), false);
            cache.put(key, beanCopier);
        } else {
            beanCopier = (BeanCopier)cache.get(key);
        }
        beanCopier.copy(srcObj, targetObj, null);
    }

    /**
     * 自定义实现转换器
     */
    private static class TestConverter implements Converter {

        @Override
        public Object convert(Object o, Class aClass, Object o1) {
            if (o instanceof Integer) {
                return String.valueOf(o);
            }
            return o;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Do {
        private Long uuid;
        private String name;
        private Integer type;
        private List<DoDetail> doDetailList;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Dto {
        private Long uuid;
        private String name;
        private String type;
        private List<DtoDetail> doDetailList;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class DoDetail {
        private Long id;
        private String detailName;
        private Integer detailType;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class DtoDetail {
        private Long id;
        private String detailName;
        private String detailType;
    }
}
