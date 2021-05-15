package com.test;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.PageUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang.StringUtils;
import cn.hutool.system.SystemUtil;
import org.apache.kafka.common.utils.ByteUtils;

import java.io.File;
import java.io.FileInputStream;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author lz-119612
 * @version 1.0
 * @date 2019/12/23 16:10
 **/
public class Test {

    //并发执行List.add操作  线程不安全
    public static void main1(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(50), new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build());

        List<String> list = Lists.newArrayList();
        CountDownLatch countDownLatch = new CountDownLatch(50);
        for (int i=0; i<50; i++) {
            executorService.execute(() -> {
                list.add("value");
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        System.out.println(list.size());
        executorService.shutdown();

    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("lz".hashCode());
        System.out.println("lz".hashCode() & 32);

        ExecutorService es = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().build());
        ExecutorService es1 = Executors.newFixedThreadPool(10, new ThreadFactoryBuilder().build());
        ExecutorService es2 = Executors.newCachedThreadPool(new ThreadFactoryBuilder().build());
        ExecutorService es3 = Executors.newScheduledThreadPool(10, new ThreadFactoryBuilder().build());

        List<Integer> list = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10,11);
        int[] startEnd = PageUtil.transToStartEnd(1, 10);
        int size = startEnd[1];
        if (list.size() < size) {
            size = list.size();
        }
        System.out.println(startEnd[0]+"  " +size);

        System.out.println(list.subList(startEnd[0], size));

        //System.out.println(JSON.toJSONString(listStartPage(Lists.newArrayList(1,2,3,4,5,6,7,8,9,10,11,12), 3 ,10)));

        List<Integer> listRoot = Lists.newArrayList(Test.list);
        List<Integer> list2 = Lists.newArrayList(3,4,5,6);
        listRoot.removeAll(list2);
        System.out.println(JSON.toJSONString(listRoot));
        System.out.println(JSON.toJSONString(Test.list));
        System.out.println(JSON.toJSONString(list2));

        Period period =Period.between(LocalDate.now(), LocalDate.of(2020, 7, 10));
        System.out.println(period.getMonths());
        System.out.println(period.isNegative());

        Instant start = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        System.out.println("start:"+start);
        Thread.sleep(1000);
        Instant end = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        Duration duration = Duration.between(start, end);
        System.out.println(duration.getSeconds() + "----" + duration.getNano());

        System.out.println(new Date().getTime());
        System.out.println(System.currentTimeMillis());
        System.out.println(Instant.now().toEpochMilli());
        System.out.println(Instant.now().getEpochSecond());
        System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(Instant.now());

        DateTimeFormatter df= DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String string = df.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(new Date().getTime()),ZoneId.of("Asia/Shanghai")));

    }

    public static final List<Integer> list = Lists.newArrayList(1,2,3,4);

    private static List<Integer> listStartPage(List<Integer> list, Integer pageNum, Integer pageSize) {
        int[] startEnd = PageUtil.transToStartEnd(pageNum - 1, pageSize);
        if (list.size() < startEnd[1]) {
            startEnd[1] = list.size();
        }
        return list.subList(startEnd[0], startEnd[1]);
    }

    public static void main2(String[] args) {
        DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String todayStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        todayStr = todayStr+" "+"08:00:00";


        LocalDateTime localDateTime = LocalDateTime.parse(todayStr, timeDtf);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        System.out.println(todayStr);
        System.out.println(date);

        String localDateTime1 = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Date date1 = Date.from(LocalDateTime.parse(todayStr, timeDtf).atZone(ZoneId.systemDefault()).toInstant());

        try {
            throw new RuntimeException("sssssssss");
        } catch (Exception e) {
            System.out.println("------" + e);
        }



    }
    public static void main33(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String str = "  10007     西安   ";
        String[] strArr = str.split(" ");
        System.out.println(JSON.toJSONString(strArr));
        List<String> list = Arrays.stream(strArr).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        System.out.println(list);


        System.out.println("530000".substring(2));

        Instant timeInstant = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        System.out.println("-------------------"+ timeInstant.getEpochSecond());

        //((Doxxx)Class.forName("Doxxx").newInstance()).test();
        System.out.println(UUID.randomUUID());
        System.out.println(cn.hutool.core.lang.UUID.fastUUID());


        System.out.println(System.getProperty("user.dir"));
        System.out.println(SystemUtil.getOsInfo().isWindows());
        String path = "D:\\image";
        String fileName = "background.jpg";
        File image = new File(path + File.separator + fileName);
        System.out.println(image.exists());
        String base64Image = Base64.encode(image);
        System.out.println(base64Image);
        byte[] imageByte = Base64.decode(base64Image);
        System.out.println(imageByte.length);
        FileUtil.writeBytes(imageByte, new File(path + File.separator + "background2.jpg"));

        FileUtil.del(path + File.separator + "background3.jpg");
    }

     class Doxxx {
         void test() {
            System.out.println("***********88");
        }
    }



}
