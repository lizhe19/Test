package com.test.thread;

import java.util.concurrent.*;

/**
 * @author lz-119612
 * @version 1.0
 * @date 2020/11/6 9:45
 *  
 **/
public class ExecutorPool {
    public static void main(String[] args) throws InterruptedException {
        int corePoolSize = 5;
        int maximumPoolSize = 10;
        long keepAliveTime = 5;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(10);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        //RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        int size = 100;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            threadPool.execute(new Worker1(i, countDownLatch));
        }
        countDownLatch.await();
        System.out.println("------------------");
        threadPool.shutdown();
    }
    static class Worker1 implements Runnable {
        private int workerName;
        private CountDownLatch countDownLatch;

        public Worker1(int s, CountDownLatch countDownLatch) {
            this.workerName = s;
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run() {
            try {
                String name = Thread.currentThread().getName();
                System.out.printf("(%s)= %d is running%n", name, this.workerName);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                countDownLatch.countDown();
            }

        }
    }
}
