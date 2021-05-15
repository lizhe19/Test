package com.test.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author 38055
 * @ClassName: CommandUtil
 * @Description: 调用linux命令
 * @Create 2019-08-30 15:41
 */
@Slf4j
public class CommandUtil {

    public static final boolean isLinux;

    static {
        String os = System.getProperties().getProperty("os.name").toLowerCase();
        isLinux = os.toLowerCase().contains("linux");
    }

    /**
     * 需要执行命令完后的返回结果 对密码的解密 采用aes 256 cbc
     * @param pwd 加密的字符串
     * @return result 解密后的处理
     */
    public static String getCmdResult(String pwd) {
        if (!isLinux) {
            return pwd;
        }
        // 执行一个命令需要展示返回结果的
        Runtime r = Runtime.getRuntime();
        StringBuffer cmd = new StringBuffer();
        cmd.append("echo \"").append(pwd).append(
            "\" | openssl enc -e -aes-256-cbc -a -K \"1a2c843546bad627257\" -iv \"af63364764737436\" -nosalt -d");
        System.out.println(cmd);
        // 命令带有管道符使用
        String[] cmds = {"/bin/sh", "-c", cmd.toString()};
        StringBuffer sb = new StringBuffer();
        try {
            Process p = r.exec(cmds);
            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("UTF-8")));
            String line = "";
            sb = new StringBuffer();
            while ((line = b.readLine()) != null) {
                sb.append(line).append("\n");
            }
            b.close();
            p.destroy();
        } catch (IOException | InterruptedException e) {
            log.error("There is an error when execute command,the error message:", e);
            sb.append(pwd);
        }
        return sb.toString();

    }
}
