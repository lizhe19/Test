package com.test.lock;

import com.alibaba.fastjson.JSON;

import java.lang.management.ManagementFactory;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock 测试
 *
 * @author lz-119612
 * @version 1.0
 * @date 2019/12/20 15:43
 **/
public class ReentrantReadWriteLockTest {
    /**
     * 在线程持有读锁的情况下，该线程不能取得写锁(因为获取写锁的时候，如果发现当前的读锁被占用，就马上获取失败，不管读锁是不是被当前线程持有)。
     * 在线程持有写锁的情况下，该线程可以继续获取读锁（获取读锁时如果发现写锁被占用，只有写锁没有被当前线程占用的情况才会获取失败）。
     * 仔细想想，这个设计是合理的：因为当线程获取读锁的时候，可能有其他线程同时也在持有读锁，因此不能把获取读锁的线程“升级”为写锁；而对于获得写锁的线程，它一定独占了读写锁，
     * 因此可以继续让它获取读锁，当它同时获取了写锁和读锁后，还可以先释放写锁继续持有读锁，这样一个写锁就“降级”为了读锁。
     *
     * 综上：一个线程要想同时持有写锁和读锁，必须先获取写锁再获取读锁；写锁可以“降级”为读锁；读锁不能“升级”为写锁。
     */
    /**
     * java.lang.management.RuntimeMXBean是Java 虚拟机的运行时系统的管理接口。使用它可以获取正在运行的 Java 虚拟机等信息，包括获取PID
     *
     * 读写锁 读读不互斥  读写互斥 写写互斥
     */
    static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    public static void main(String[] args) throws InterruptedException {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        //System.out.println(JSON.toJSONString(ManagementFactory.getRuntimeMXBean()));
        System.out.println("*******************"+name);
        int index = name.indexOf('@');
        long processId = Long.parseLong(name.substring(0, index));

        System.out.println("*******************"+processId);

        reentrantReadWriteLock.readLock().lock();
        new Thread(() -> {
            reentrantReadWriteLock.writeLock().lock();
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            reentrantReadWriteLock.writeLock().unlock();
        }).start();
        Thread.sleep(10000);
        System.out.println("------------------");
        reentrantReadWriteLock.readLock().unlock();



    }

    private static ReentrantLock lock = new ReentrantLock();

    public static void main1(String[] args) throws InterruptedException {
        lock.lock();
        new Thread(() -> {
            lock.lock();
            System.out.println("-----------------------"+Thread.currentThread().getName());
            lock.unlock();
        }).start();
        Thread.sleep(5000L);
        System.out.println("***************"+Thread.currentThread().getName());
        lock.unlock();
    }
}
