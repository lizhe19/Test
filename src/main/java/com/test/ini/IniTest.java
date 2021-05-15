package com.test.ini;

import cn.hutool.core.util.CharsetUtil;

import java.io.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lz-185979
 * @version 1.0
 * @date 2021/4/22?17:50
 **/
public class IniTest {
    public static void main(String[] args) throws Exception {
        //ini文件的存放位置
        String filepath = "D:\\SCADA\\bin\\FEPConfig\\MacsDriver.ini";
        //创建文件输入流
//		FileInputStream fis = new FileInputStream(filepath);
        InputStreamReader bufferedReader = new InputStreamReader (new FileInputStream(filepath), CharsetUtil.GBK);
        //创建文件输出流
        OutputStream opt = null;
        //创建Properties属性对象用来接收ini文件中的属性
        Properties pps = new Properties();
        //从文件流中加载属性
        pps.load(bufferedReader);
        //通过getProperty("属性名")获取key对应的值
        System.out.println(pps.getProperty("FepModbus_SERIAL"));
        System.out.println(pps.getProperty("PrtclType"));
        //加载读取文件流
		/*opt = new FileOutputStream(filepath);

		//通过setProperty(key,value)赋值，会覆盖相同key的值
		pps.setProperty("url2", "v2");
		pps.setProperty("url1", "v1");
		//修改值 (必不可少)
		pps.store(opt, null);
		opt.close();*/

        System.out.println(getProfileString(filepath, "FepModbus_TCPIP", "WatchTime", null).split(";")[0].trim());
    }

    public static String getProfileString(String file, String section,
                                          String variable, String defaultValue) throws IOException {
        String strLine, value = "";
        BufferedReader bufferedReader
                = new BufferedReader(new InputStreamReader(new FileInputStream(file), CharsetUtil.GBK));
        boolean isInSection = false;
        try {
            while ((strLine = bufferedReader.readLine()) != null) {
                strLine = strLine.trim();
                Pattern p;
                Matcher m;
                p = Pattern.compile("\\["+section+"]");
                m = p.matcher((strLine));
                if (m.matches()) {
                    p = Pattern.compile("\\["+section+"]");
                    m = p.matcher(strLine);
                    if (m.matches()) {
                        isInSection = true;
                    } else {
                        isInSection = false;
                    }
                }
                if (isInSection == true) {
                    strLine = strLine.trim();
                    String[] strArray = strLine.split("=");
                    if (strArray.length == 1) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(variable)) {
                            value = "";
                            return value;
                        }
                    } else if (strArray.length == 2) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(variable)) {
                            value = strArray[1].trim();
                            return value;
                        }
                    } else if (strArray.length > 2) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(variable)) {
                            value = strLine
                                    .substring(strLine.indexOf("=") + 1).trim();
                            return value;
                        }
                    }
                }
            }
        } finally {
            bufferedReader.close();
        }
        return defaultValue;
    }
}
