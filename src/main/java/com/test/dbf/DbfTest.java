package com.test.dbf;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.CharsetUtil;
import com.linuxense.javadbf.DBFReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lz-185979
 * @version 1.0
 * @date 2021/4/22?17:17
 **/
public class DbfTest {
    public static void main(String[] args) throws IOException {
        //        ResourceUtil.getStream("fepConf/Help.dbf")
//        System.out.println("*******************"+FileUtil.exist("fepConf/Help.dbf")+"*******************"+FileUtil.exist("fepConf/Help.dbf1"));
//        System.out.println("*******************"+FileUtil.exist("D:\\SCADA\\bin\\FEPConfig\\Help.dbf")+"*******************"+FileUtil.exist("D:\\SCADA\\bin\\FEPConfig\\Help.dbf1"));

        List<Map<String, String>> list = readDbf("D:\\SCADA\\bin\\FEPConfig\\Help.dbf");
        System.out.println(list.stream().filter(o -> "1".equals(o.get("ISUSE")) && "PROTOCOL".equals(o.get("TYPE")))
                .map(o -> o.get("NETWORKTYP")).distinct().collect(Collectors.toList()));
    }
    public static List<Map<String, String>> readDbf(String path) throws IOException {
        List<Map<String, String>> rowList = new ArrayList<>();
//        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("files/filterPoi.txt");
//        ResourceUtil.getStream()

        DBFReader dbfReader = new DBFReader(new FileInputStream(path), CharsetUtil.CHARSET_GBK);
        Object[] rowValues;
        while ((rowValues = dbfReader.nextRecord()) !=null) {
            Map<String, String> rowMap = new HashMap<>();
            for (int i = 0; i < rowValues.length; i++) {
                rowMap.put(dbfReader.getField(i).getName(), String.valueOf(rowValues[i]).trim());
            }
            rowList.add(rowMap);
        }
        dbfReader.close();
        return rowList;
    }
}
