package com.test.model;

/**
 * 枚举实现工厂模式
 *
 * @author lz-119612
 * @version 1.0
 * @date 2020/4/2 14:28
 **/
public class ModelTest {

    public interface OperateService {
        int apply(int a, int b);
    }

    public enum OperatorEnum implements OperateService{
        //加和
        ADD {
            @Override
            public int apply(int a, int b) {
                return a + b;
            }
        },
        //减法
        REDUCE {
            @Override
            public int apply(int a, int b) {
                return a - b;
            }
        };

    }

    public static void main(String[] args) {
        String type = "REDUCE";
        int result = OperatorEnum.valueOf(type).apply(2, 1);

        System.out.println("result=" +result);
    }



}
