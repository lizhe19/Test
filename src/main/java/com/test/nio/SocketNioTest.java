package com.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * socket客户端
 *
 * @author lz-185979
 * @version 1.0
 * @date 2021/5/14 14:09
 **/
public class SocketNioTest {

    public static void main(String[] args) {
        client();
    }

    public static void client() {
        try {
            //初始化客户端
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            //注册连接事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            //发起连接
            socketChannel.connect(new InetSocketAddress("localhost", 8888));
            //开启控制台输入监听
            new ChatThread(selector, socketChannel).start();
            Calendar ca = Calendar.getInstance();
            //轮询处理
            while (true) {
                if (socketChannel.isOpen()) {
                    //在注册的键中选择已准备就绪的事件
                    selector.select();
                    //已选择键集
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    //处理准备就绪的事件
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        //删除当前键，避免重复消费
                        iterator.remove();
                        //连接
                        if (key.isConnectable()) {
                            //在非阻塞模式下connect也是非阻塞的，所以要确保连接已经建立完成
                            while (!socketChannel.finishConnect()) {
                                System.out.println("连接中");
                            }
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        }
                        //控制台监听到有输入，注册OP_WRITE,然后将消息附在attachment中
                        if (key.isWritable()) {
                            //发送消息给服务端
                            socketChannel.write((ByteBuffer) key.attachment());
                            /*
	                            已处理完此次输入，但OP_WRITE只要当前通道输出方向没有被占用
	                            就会准备就绪，select()不会阻塞（但我们需要控制台触发,在没有输入时
	                            select()需要阻塞），因此改为监听OP_READ事件，该事件只有在socket
	                            有输入时select()才会返回。
                            */
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println("==============" + ca.getTime() + " ==============");
                        }
                        //处理输入事件
                        if (key.isReadable()) {

                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 4);
                            int len = 0;
                            //捕获异常，因为在服务端关闭后会发送FIN报文，会触发read事件，但连接已关闭,此时read()会产生异常
                            try {
                                if ((len = socketChannel.read(byteBuffer)) > 0) {
                                    System.out.println("接收到來自服务器的消息\t");
                                    System.out.println("[receive from server]: " + new String(byteBuffer.array(), 0, len));
                                }
                            } catch (IOException e) {
                                System.out.println("服务器异常，请联系客服人员!正在关闭客户端.........");
                                key.cancel();
                                socketChannel.close();
                            }
                            System.out.println("=========================================================");
                        }
                    }
                } else {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("客户端异常，请重启！");
        }
    }

    static class ChatThread extends Thread {

        private Selector selector;
        private SocketChannel socketChannel;

        public ChatThread(Selector selector, SocketChannel socketChannel) {
            super();
            this.selector = selector;
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            try {
                //等待连接建立
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入您要发送给服务端的消息");
            System.out.println("=========================================================");
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                try {
                    //用户已输入，注册写事件，将输入的消息发送给客户端
                    socketChannel.register(selector, SelectionKey.OP_WRITE, ByteBuffer.wrap(s.getBytes()));
                    //唤醒之前因为监听OP_READ而阻塞的select()
                    selector.wakeup();
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
