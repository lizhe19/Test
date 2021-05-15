package com.test.nio;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Scanner;

/**
 * socket服务端
 *
 * @author lz-185979
 * @version 1.0
 * @date 2021/5/14 14:12
 **/
public class ServerSocketNioTest {
    // 端口号
    private static final int PORT = 8888;
    // 缓存大小
    private static final int BUFFER_SIZE = 1024;
    // 控制台输入流
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        new ServerSocketNioTest().service();
    }
    Calendar ca = Calendar.getInstance();
    public void service() throws IOException {
        // 初始化服务器
        System.out.println("Initialize server...");
        // 1. 创建ServerSocketChannel通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2. 设置通道为非阻塞式
        serverSocketChannel.configureBlocking(false);
        // 3. 创建选择器
        Selector selector = Selector.open();
        // 4. 绑定服务器 //InetAddress.getLocalHost()
        serverSocketChannel.bind(new InetSocketAddress(PORT));
        // 5. 注册接收事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 与客户端建立连接
        while (selector.select() > 0) {
            // 1. 获得通道的迭代器
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            // 2. 遍历通道
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 3. 获得该通道后，就将其从迭代器中删除
                iterator.remove();
                // 4. 根据不同的通道进行不同的业务
                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        System.out.println("connect with client...");
                        // 接收业务
                        accept(selector,key);
                    }
                    if (key.isReadable()) {
                        System.out.print("receive from client: ");
                        // 读取业务
                        /*
                            服务器端的读取业务和客户端的是一模一样的，
                            于是这里直接调用客户端的读取业务
                         */
                        read(selector,key);
                    }
                    if (key.isWritable()) {
                        System.out.print("send to client: ");
                        // 写出业务
                        write(selector,key);
                    }
                }
            }
        }
    }

    /**
     * 接收业务
     * @param selector
     * @param key
     */
    private void accept(Selector selector, SelectionKey key) throws IOException {
        // 1. 获取服务器通道
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        // 2. 获取TCP协议传输通道
        SocketChannel socketChannel = serverSocketChannel.accept();
        // 3. 将传输通道设为非阻塞式
        socketChannel.configureBlocking(false);
        // 4. 绑定读取事件
        socketChannel.register(selector,SelectionKey.OP_READ);
        // 接收成功
        System.out.println("connect with a client from " + socketChannel.socket().getInetAddress().getHostAddress());

    }

    /**
     * 读取业务
     * @param selector
     * @param key
     */
    public static void read(Selector selector, SelectionKey key) throws IOException {
        // 1. 获得TCP协议通信的通道
        SocketChannel socketChannel = (SocketChannel) key.channel();
        // 2. 分配缓存区
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        // 创建字节数组
        byte[] bytes = new byte[BUFFER_SIZE * 2];
        // 设置下标
        int index = 0;
        // 3. 读取数据到缓冲区
        while (socketChannel.read(buffer) != 0) {
            // 4. 将缓存区的指针回到初始位置
            buffer.flip();
            // 5. 不断读出数据
            while (buffer.hasRemaining()) {
                bytes[index++] = buffer.get();
            }
            // 读取完毕后，清空缓冲区
            buffer.clear();
        }
        System.out.println("[receive from client]: "+ new String(bytes, 0, index, StandardCharsets.UTF_8));
        // 6. 注册写出业务
        //socketChannel.register(selector,SelectionKey.OP_WRITE, ByteBuffer.wrap("???????????".getBytes()));

        //开启控制台输入监听
        new ServerSocketNioTest.ChatThread(selector, socketChannel).start();

    }

    /**
     * 写出业务
     * @param selector
     * @param key
     * @throws IOException
     */
    private void write(Selector selector, SelectionKey key) throws IOException {
        // 1. 获得TCP协议通信的通道
        SocketChannel socketChannel = (SocketChannel) key.channel();
        // 2. 分配缓存区
        /*ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        // 3. 清空缓存区
        buffer.clear();
        // 4. 写入发送的信息
        buffer.put(ByteBuffer.wrap(scanner.nextLine().getBytes(StandardCharsets.UTF_8)));
        // 5. 将缓存区的指针回到初始位置
        buffer.flip();
        // 6. 设置通道为非阻塞式
        socketChannel.configureBlocking(false);
        // 7. 不断写出到通道
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        socketChannel.write((ByteBuffer) key.attachment());*/
        socketChannel.write((ByteBuffer) key.attachment());
        // 8. 继续注册读取事件
        socketChannel.register(selector,SelectionKey.OP_READ);
        System.out.println("==============" + ca.getTime() + " ==============");
    }

    static class ChatThread extends Thread {

        private Selector selector;
        private SocketChannel socketChannel;

        public ChatThread(Selector selector, SocketChannel socketChannel) {
            super();
            this.selector = selector;
            this.socketChannel = socketChannel;
        }

        @SneakyThrows
        @Override
        public void run() {
            try {
                //等待连接建立
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入您要发送给客户端的消息");
            System.out.println("=========================================================");
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                try {
                    //用户已输入，注册写事件，将输入的消息发送给客户端
                    socketChannel.register(selector, SelectionKey.OP_WRITE, ByteBuffer.wrap(s.getBytes()));
                    //唤醒之前因为监听OP_READ而阻塞的select()
                    //selector.wakeup();
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
