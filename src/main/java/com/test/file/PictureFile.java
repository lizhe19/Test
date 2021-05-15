package com.test.file;

import com.google.common.collect.Maps;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *  * 图片文件
 *  *
 *  @author lz-119612
 *  @version 1.0
 *  @date 2020/3/17 17:39
 *  
 **/
public class PictureFile {
    public static void main(String[] args) {
        //System.out.println(LocalDate.now());

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        System.out.println(localDateTime.toEpochSecond(ZoneOffset.of("+8")));
        System.out.println(localDateTime.plusHours(-1));
        System.out.println(localDateTime.plusMinutes(-5));
        System.out.println("--------------------------------------------------");
        //long time = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        long time = 1603151999;
        System.out.println("time=" + time);
        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault());
        System.out.println(localDateTime1 + "-----" +ZoneId.systemDefault());
        System.out.println(localDateTime1.toEpochSecond(ZoneOffset.of("+8")));
        System.out.println(localDateTime1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));


    }
    public static void main00(String[] args) {
        String dicName = LocalDate.now().plusDays(-8).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String preDicName = LocalDate.now().plusDays(-9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String path = String.format("/opt/picture/%s/", dicName);
        if (System.getProperties().getProperty("os.name").toLowerCase().contains("windows")) {
            path = String.format("%s/picture/%s/", System.getProperty("user.dir"), dicName);
        }
        deleteFileOrDirectory(path);

        String prePath = String.format("/opt/picture/%s/", preDicName);
        if (System.getProperties().getProperty("os.name").toLowerCase().contains("windows")) {
            prePath = String.format("%s/picture/%s/", System.getProperty("user.dir"), preDicName);
        }
        deleteFileOrDirectory(prePath);
    }

    public static void main1(String[] args) throws Exception {
        File file = new File("D:\\picture\\pic.jpg");

        System.out.println(System.getProperty("user.dir"));
        byte[] bytes = fileToByte(file);
        int num = bytes == null ? 0 : bytes.length;
        System.err.println("num: " + num);
        //String path = System.getProperty("user.dir") + "/picture/" + LocalDate.now() + "/device" + "-1024-" + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss")) + ".jpg";
        String path = String.format("%s/picture/%s/%s-%s-%s.jpg", System.getProperty("user.dir"), LocalDate.now(), "devicecode", 1024, LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss")));
        //path=D:\dev\test/picture/2020-04-07/devicecode-1024-112039.jpg
        System.out.println("path="+path);
        byteToFile(path, bytes);
    }

    public static void main333(String[] args) throws Exception {
        byte[] bytes = getPictureUrl("http://10.80.50.179:8314/video_pic/2020-07-01/bGX8MYdmA1C55AJI7TU6C7-bGX8MYdmA1C55AJI82MT8R-133701.jpg");
        System.out.println("bytes.length="+bytes.length);
        byte[] bytes2 = getPictureDisk("D:\\picture\\pic.jpg");
        System.out.println("bytes2.length="+bytes2.length);

        byteToFile("D:\\picture\\test1.jpg", bytes);
        byteToFile("D:\\picture\\test2.jpg", bytes2);

    }

    public static void deleteFileOrDirectory(String path) {
        System.out.println("path="+path);
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("需要删除的目录或文件不存在!");
        } else {
            //文件类型
            if (file.isFile()) {
                System.out.println("-----------------"+file.delete());
            } else {
                //目录类型  要先删除目录下文件  再删除目录
                File[] fileArr = file.listFiles();
                if (null != fileArr) {
                    for (File newFile : fileArr) {
                        System.out.println("delete file " + newFile.getName() + " " + newFile.delete());
                    }
                }
                System.out.println("delete directory "+file.delete());
            }
        }
    }




    public static byte[] fileToByte(File img) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage bi;
            bi = ImageIO.read(img);
            ImageIO.write(bi, "jpg", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos.close();
        }
        return null;
    }

    public static void byteToFile(String path , byte[] bytes)throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        BufferedImage bi1 =ImageIO.read(bais);
        try {
            //可以是jpg,png,gif格式
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            //不管输出什么格式图片，此处不需改动
            ImageIO.write(bi1, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            bais.close();
        }
    }


    /**
     * 读取网络文件 转为字节数组
     * @param url net图片url
     * @return byte数组
     */
    private static byte[] getPictureUrl(String url) {
        InputStream inStream = null;
        ByteArrayOutputStream outStream = null;
        try {
        URL urlConet = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlConet.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(4 * 1000);
        //通过输入流获取图片数据
        inStream = con.getInputStream();
        outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[inStream.available()];
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            return outStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(inStream);
            IOUtils.closeQuietly(outStream);
        }


    }


    /**
     * 读取本地文件，转为字节数组
     *
     * @param url 本地文件路径
     * @return byte
     */
    private static byte[] getPictureDisk(String url) {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(url));
             ByteArrayOutputStream out = new ByteArrayOutputStream(1024)) {
            byte[] temp = new byte[in.available()];
            int size;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 读取网络文件 转为字节数组
     * @param url net图片url
     * @return InputStream
     */
    public static InputStream getPictureInputStreamUrl(String url) {
        InputStream inputStream = null;
        try {
            URL urlConet = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlConet.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(4 * 1000);
            //通过输入流获取图片数据
            inputStream = con.getInputStream();
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }


    }


    /**
     * 读取本地文件，转为字节数组
     *
     * @param url 本地文件路径
     * @return inputStream
     */
    public static InputStream getPictureInputStreamDisk(String url) {
        try (InputStream inputStream = new FileInputStream(url)) {
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void fileDownLoad(HttpServletResponse response, InputStream inputStream) throws IOException {
        if (null == inputStream) {
            System.out.println("[fileDownLoad] inputStream is null");
            return;
        }
        OutputStream out = response.getOutputStream();
        int len;
        byte[] bytes = new byte[inputStream.available()];
        while ((len = inputStream.read(bytes)) > 0) {
            out.write(bytes, 0, len);
        }
    }

}
