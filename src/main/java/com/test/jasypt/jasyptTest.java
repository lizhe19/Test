package com.test.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lz-119612
 * @version 1.0
 * @date 2020/6/29 15:24
 *  
 **/
public class jasyptTest {

   /* @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void test(String[] args) {
        System.out.println("PWD: " + stringEncryptor.encrypt("yourpassword"));
    }*/


    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("G0CvDz7oJn6");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("root123");
        System.out.println("username:"+username);
        System.out.println("password:"+password);
        System.out.println(textEncryptor.decrypt(username));
        System.out.println(textEncryptor.decrypt(password));
    }
}
