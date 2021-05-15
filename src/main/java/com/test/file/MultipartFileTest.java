package com.test.file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

/**
 * 测试类1
 *
 * @author lz-119612
 * @version 1.0
 * @date 2019/12/20 15:02
 **/
public class MultipartFileTest {

    /**
     * 下载图片Url转MultipartFile
     * @param args
     */
    public static void main(String[] args) {
        try {
            File file = new File("d:/test.jpg");
            FileUtils.copyURLToFile(new URL("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1742889286,3721348350&fm=26&gp=0.jpg"), file);

            FileItemFactory factory = new DiskFileItemFactory(16, null);
            FileItem item = factory.createItem(file.getName(), "text/plain", true, file.getName());
            int bytesRead;
            byte[] buffer = new byte[8192];
            try (FileInputStream fis = new FileInputStream(file);
                 OutputStream os = item.getOutputStream()) {
                while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
            MultipartFile multipartFile = new CommonsMultipartFile(item);
            //todo handle here
            System.out.println(multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
