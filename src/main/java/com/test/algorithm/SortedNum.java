package com.test.algorithm;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 生成连续整数
 *
 * @author lz-119612
 * @version 1.0
 * @date 2019/11/17 9:44
 *  
 **/
public class SortedNum {
    public static void main(String[] args) {
        List<Integer> numbers1 = IntStream.rangeClosed(11, 100).boxed().collect(Collectors.toList());
        System.out.println("---numbers1----"+ JSON.toJSONString(numbers1));
        List<Integer> numbers2 = Stream.iterate(11, n -> n + 1).limit(90).collect(Collectors.toList());
        System.out.println("----numbers2---"+JSON.toJSONString(numbers2));
        List<Integer> numbers3 = Arrays.stream(ArrayUtil.range(11, 101, 1)).boxed().collect(Collectors.toList());
        System.out.println("----numbers3---"+JSON.toJSONString(numbers3));
    }
}
