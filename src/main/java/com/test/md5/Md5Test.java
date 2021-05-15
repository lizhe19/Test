package com.test.md5;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

public class Md5Test {


    /**
     * 使用hutool工具
     */
    public static void main2(String[] args) {
        String str = "123456";
        String md5Str = SecureUtil.md5(str);
        System.out.println(md5Str);

        String pushUrl = "rtmp://cdn1.fjgsgl.cn:1935/rtmp/733a9d57-1170-4ba9-b02d-4d3e593ad0f6_low?k=f8e31284df16f0649072d32a2cfb31bb&time=1602668832";
        String md5Key = "AgfGkzOm7vKeA5ir";
        //String md5Key = "123";
        String uri = "/rtmp/733a9d57-1170-4ba9-b02d-4d3e593ad0f6_low";
        long time = 1602668832;
        String key = md5(md5Key + uri + time) + "&time=" + time;
        System.out.println("----------------"+key);



    }

    /**
     * 自己实现Md5方法
     */
    public static void main1(String[] args) {
        String pushUrl = "rtmp://cdn1.fjgsgl.cn:1935/rtmp/0cfa08fc-875a-4768-8d55-8342e462c88d_low?k=e6d8e02055234f978adcbc873d3f281f&time=1602667108";
        String md5Key = "AgfGkzOm7vKeA5ir";
        String uri = "/rtmp/0cfa08fc-875a-4768-8d55-8342e462c88d_low";
        long time = 1602667108;
        String key = md5(md5Key + uri + time) + "&time=" + time;
        System.out.println("----------------"+key);


        /*String url = "http://play.dahuatech.com/rtmp/86c202fa-a5d4-37e7-9a58-933f649f97ef-low/playlist.m3u8?k=f09cf079a0c15fe64d37cc736c11b7ba&time=1588924581";

        System.out.println(" **************" + md5("123/rtmp/4e8b7f10-b893-3621-8c1a-13e9308a7f79_low1591778770"));
        System.out.println(" **************" + md5("123/rtmp/4e8b7f10-b893-3621-8c1a-13e9308a7f79_low1591778770"));

        List<Dto> list = Lists.newArrayList(new Dto("id1", "name1"), new Dto("id2", "name2"), new Dto("id1", "name3"));*/

    }
    @Data
    @NoArgsConstructor
    private static class Dto {
        Dto(String id, String name, String age) {
            this.id=id;
            this.name=name;
            this.age=age;
        }
        private String id;
        private String name;
        private String age;
    }

    public static void main(String[] args) {
        Dto dto1 = new Dto("1", "name1", null);
        Dto dto2 = new Dto("1", "name1", null);
        Dto dto3 = new Dto("1", "name2", null);
        List<Dto> list = Lists.newArrayList(dto1, dto2, dto3);
        List<Dto> listNew = Lists.newArrayList();
        /*list.stream().forEach(o -> {
            if (!listNew.contains(o)) {
                listNew.add(o);
            }
        });
        System.out.println("result="+JSON.toJSONString(listNew));*/
        System.out.println(JSON.toJSONString(list));
        System.out.println(JSON.toJSONString(list.stream().distinct().collect(Collectors.toList())));

    }


    /**
     * MD5加密方法
     * @param param 入参
     * @return 解密够的字符串
     */
    public static String md5(String param) {
        String md5Str = null;
        char[] hexDigist = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(param.getBytes());
            //16个字节的长整数
            byte[] datas = md.digest();
            char[] str = new char[2 * 16];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte b = datas[i];
                //高4位
                str[k++] = hexDigist[b >>> 4 & 0xf];
                //低4位
                str[k++] = hexDigist[b & 0xf];
            }
            md5Str = new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Str;
    }
}
