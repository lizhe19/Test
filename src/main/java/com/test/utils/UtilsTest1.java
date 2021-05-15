package com.test.utils;

import com.test.system.SystemTest;
import org.springframework.context.annotation.Bean;

/**
 * @author lz-119612
 * @version 1.0
 * @date 2019/12/23 10:33
 **/
public class UtilsTest1 {
    public static String getUnicode(String source){
        String returnUniCode=null;
        String uniCodeTemp=null;
        for(int i=0;i<source.length();i++){
            //使用char类的charAt()的方法
            uniCodeTemp = "\\u" + Integer.toHexString(source.charAt(i));
            returnUniCode = returnUniCode == null ? uniCodeTemp : returnUniCode + uniCodeTemp;
        }
        //返回一个字符的unicode的编码值
        return returnUniCode;
    }
}
