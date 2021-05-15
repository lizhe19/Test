package com.test.collection;

import com.google.common.collect.Lists;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 集合测试
 *
 * @author lz-119612
 * @version 1.0
 * @date 2020/4/14 9:31  
 **/
public class CollectionTest1 {
    //ConcurrentModificationException会发生
    //static List<Integer> list = Lists.newCopyOnWriteArrayList();

    static CopyOnWriteArrayList<Integer> LIST = Lists.newCopyOnWriteArrayList(() -> Lists.newArrayList(100,200,300).iterator());

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            LIST.forEach(o -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("o1="+ o);
            });
        }).start();

        Thread.sleep(1500);
        for (int i=0;i<=2;i++) {
            LIST.add(i);
            System.out.println("list.add="+i);
        }

        new Thread(() -> {
            LIST.forEach(o -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("o2="+ o);
            });
        }).start();

        Thread.sleep(100000000);
    }
}
