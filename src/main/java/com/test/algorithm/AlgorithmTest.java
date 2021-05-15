package com.test.algorithm;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author lz-119612
 * @version 1.0
 * @date 2019/12/23 9:06
 **/
public class AlgorithmTest {

    public static void main(String[] args) {
        //拆分list
        List<Integer> list = Lists.newArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        int start = 0, temp = 3, end = temp;
        while (true) {
            if (start >= list.size()) {
                list.clear();
                break;
            }
            if (end > list.size()) {
                end = list.size();
            }

            List<Integer> list1 = list.subList(start, end);

            serviceHandle(list1);

            start += temp;
            end += temp;



        }
    }

    /**
     * 业务逻辑写在这
     *
     */
    private static <T> void serviceHandle(List<T> list1) {
        System.out.println(list1);
    }
}
