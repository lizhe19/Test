package com.test.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *  * 测试类  博客 https://blog.csdn.net/newchitu/article/details/94614348
 *  *       https://www.cnblogs.com/sweetchildomine/p/8807059.html
 *  * @author lz-119612
 *  * @version 1.0
 *  * @date 2020/3/14 10:42
 *  
 **/
public class ThreadDemo {


    /**
     * ThreadLocal初始化方法 用线程池时候因为现场复用会出现问题
     */
     static ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "";
        }
    };
        //java自带可传递threadLocal 用线程池时候因为现场复用会出现问题
     static ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<String>() {
         @Override
         protected String initialValue() {
             return "";
         }
     };
        //阿里可传递threadLocal 用线程池时候也没问题
     static ThreadLocal<String> transmittableThreadLocal = new TransmittableThreadLocal<String>() {
         @Override
         protected String initialValue() {
             return "";
         }
     };
    /**
     * java8方式
     */
    static ThreadLocal<String> threadLocal1 = ThreadLocal.withInitial(() -> "");

    static ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main4(String[] args) throws InterruptedException {
        executorService.execute(() -> {
            transmittableThreadLocal.set("aaa");
        });
        executorService.execute(() -> {
            transmittableThreadLocal.set("bbb");
        });
        executorService.execute(() -> {
            transmittableThreadLocal.set("ccc");
        });
        TimeUnit.SECONDS.sleep(2);
        executorService.execute(() -> {
            System.out.println("--------11"+transmittableThreadLocal.get());
            System.out.println("--------12"+transmittableThreadLocal.get());

        });
        executorService.execute(() -> {
            System.out.println("--------21"+transmittableThreadLocal.get());
            System.out.println("--------22"+transmittableThreadLocal.get());

        });

    }

    public static void main3(String[] args) {
            Runnable thread1 = () -> {
                threadLocal.set("thread1");
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread1=" + threadLocal.get());
            };
        Runnable thread2 = () -> {
            threadLocal.set("thread2");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread2=" + threadLocal.get());
        };
        Runnable thread3 = () -> {
            threadLocal.set("thread3");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread3=" + threadLocal.get());
        };
        new Thread(thread1).start();
        new Thread(thread2).start();
        new Thread(thread3).start();

        inheritableThreadLocal.set("test");
        new Thread(() -> {
            System.out.println("inheritableThreadLocal="+inheritableThreadLocal.get());
            inheritableThreadLocal.set("test2");
            System.out.println("inheritableThreadLocal="+inheritableThreadLocal.get());

        }).start();
    }



    public static void main2(String[] args) {
        String path = System.getProperty("user.dir") + "/picture/" + LocalDate.now() + "/device" + "1024" + ".jpg";
        System.out.println(path);
        //LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.of("+8"));
        LocalDate date = LocalDate.now();
        System.out.println("date = " + date);
        System.out.println(date.toString());
        System.out.println(date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        System.out.println(String.format("年=%d,月=%d,日=%d", date.getYear(), date.getMonthValue(), date.getDayOfMonth()));

        //设置时间
        LocalDate date1 = LocalDate.of(2020, 3, 13);
        System.out.println("date1=" + date1);

        //默认系统时区
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println("默认系统时区=" + zoneId);

        //date转LocalDateTime
        date2LocalDateTime(new Date());

        //LocalDateTime转date
        localDateTime2Date(LocalDateTime.now());

        //设置时区
        zoneSet();
    }

    /**
     * Date转换为LocalDateTime
     */
    public static void date2LocalDateTime(Date date){

        LocalDateTime ldt = date.toInstant()
                .atZone(ZoneId.systemDefault() )
                .toLocalDateTime();

        LocalDateTime ldt2 = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

        System.out.println("[date2LocalDateTime]" + ldt);
    }

    /**
     * LocalDateTime转换为Date
     */
    public static void localDateTime2Date(LocalDateTime localDateTime){

        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        System.out.println("[localDateTime2Date]" + date);
    }


    /**
     * 设置时区
     */
    public static void zoneSet() {

        // 上海时间
        ZoneId shanghaiZoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime shanghaiZonedDateTime = ZonedDateTime.now(shanghaiZoneId);

        // 东京时间
        ZoneId tokyoZoneId = ZoneId.of("Asia/Tokyo");
        ZonedDateTime tokyoZonedDateTime = ZonedDateTime.now(tokyoZoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("上海时间: " + shanghaiZonedDateTime.format(formatter));
        System.out.println("东京时间: " + tokyoZonedDateTime.format(formatter));
    }


    static final InheritableThreadLocal<String> threadParam = new InheritableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        //固定池内只有存活3个线程
        ExecutorService execService = Executors.newFixedThreadPool(3);
        //死循环几次才能看出效果
        while (true) {
            //线程1,里面有两个子线程
            Thread t = new Thread(()->{
                threadParam.set("abc");
                System.out.println("t1:" + threadParam.get());
                Thread t2 = new Thread(()->{
                    System.out.println("t2:" + threadParam.get());
//                        threadParam.remove();
                });
                execService.execute(t2);

                Thread t3 = new Thread(()->{
                    System.out.println("t3:" + threadParam.get());
//                        threadParam.remove();
                });
                execService.execute(t3);

            });
            execService.execute(t);
            TimeUnit.SECONDS.sleep(1);
            //线程4,线程1同级
            Thread t4 = new Thread(()-> {
                threadParam.set("CBA");
                System.out.println("t4:" + threadParam.get());
            });
            execService.execute(t4);
        }
    }



}
