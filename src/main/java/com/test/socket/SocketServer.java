package com.test.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socker服务端
 *
 * @author lz-119612
 * @version 1.0
 * @date 2020/8/3 10:08
 *  
 **/
public class SocketServer {
    //监听的端口号
    private static final int port = 8080;

    public static void main(String[] args) {
        System.out.println("Server...\n");
        SocketServer server = new SocketServer();
        server.init();
    }

    public void init() {
        try {
            //创建一个ServerSocket，这里可以指定连接请求的队列长度
            //new ServerSocket(port,3);意味着当队列中有3个连接请求时，如果Client再请求连接，就会被Server拒绝
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                //从请求队列中取出一个连接
                Socket client = serverSocket.accept();
                // 处理这次连接
                new HandlerThread(client);
            }
        } catch (Exception e) {
            System.out.println("服务器异常: " + e.getMessage());
        }
    }

    private class HandlerThread implements Runnable {
        private Socket socket;
        public HandlerThread(Socket client) {
            socket = client;
            //线程在这里启动
            new Thread(this).start();
        }

        @Override
        public void run() {
            try {
                // 读取客户端数据
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
                String clientInputStr = input.readLine();
                // 处理客户端数据
                System.out.println("客户端发过来的内容:" + clientInputStr);

                // 向客户端回复信息
                PrintStream out = new PrintStream(socket.getOutputStream());
                System.out.print("请输入:\t");
                // 发送键盘输入的一行
                String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
                out.println(s);

                out.close();
                input.close();
            } catch (Exception e) {
                System.out.println("服务器 run 异常: " + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        socket = null;
                        System.out.println("服务端 finally 异常:" + e.getMessage());
                    }
                }
            }
        }
    }
}
