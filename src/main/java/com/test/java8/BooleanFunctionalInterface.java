package com.test.java8;

/**
 * 自定义函数式接口
 * @author lz-119612
 * @version 1.0
 * @date 2021/1/20 13:57
 *  
 **/
@FunctionalInterface
public interface BooleanFunctionalInterface<T> {
    Boolean exec(T t);
}
