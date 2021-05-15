package com.test.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  bio-阻塞io  nio-非阻塞io  nio分为：文件nio和网络nio(tcp:[server-socket/socket]，udp)
 *  nio三大核心：channel，buffered，selector
 *
 *  详情参考：https://www.zhihu.com/question/342093305
 *
 * @author lz-185979
 * @version 1.0
 * @date 2021/5/14 10:55
 **/
public class FileNioTest {
    public static void main(String[] args) {
        fileBioMethod();
        fileNioMethod();
        fileNioMethod2();
    }

    /**
     * 文件bio
     */
    private static void fileBioMethod() {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\work\\test\\wendang.txt")))) {
            String result;
            while ((result =bufferedReader.readLine()) != null) {
                System.out.println("**************"+result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件nio
     */
    private static void fileNioMethod() {
        try(FileInputStream inputStream = new FileInputStream("D:\\work\\test\\wendang.txt");) {
            FileChannel fileChannel = inputStream.getChannel();
            //ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            ByteBuffer byteBuffer = ByteBuffer.allocate(inputStream.available() + 1);
            while (fileChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                System.out.println("-----------"+new String(byteBuffer.array()));
                byteBuffer.compact();
                System.out.println(fileChannel.read(byteBuffer));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void fileNioMethod2(){
        RandomAccessFile aFile = null;
        try{
            aFile = new RandomAccessFile("D:\\work\\test\\wendang.txt","rw");
            FileChannel fileChannel = aFile.getChannel();
//          ByteBuffer buf = ByteBuffer.allocate(1024);
            ByteBuffer buf = ByteBuffer.allocate((int)aFile.length()+1);
            int bytesRead = fileChannel.read(buf);
            while(bytesRead != -1)
            {
                buf.flip();
                System.out.println("=========="+new String(buf.array()));
                buf.compact();
                bytesRead = fileChannel.read(buf);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(aFile != null){
                    aFile.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
