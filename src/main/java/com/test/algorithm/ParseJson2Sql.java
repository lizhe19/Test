package com.test.algorithm;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * json转sql
 *
 * @author lz-119612
 * @version 1.0
 * @date 2020/8/25 10:46
 *  
 **/
public class ParseJson2Sql {
    public static void main1(String[] args) {
        //这里先填路线编码
        String lineCode = "9";

        String str = "{\"stations\":[{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"城乡\",\"station_num\":\"1\",\"poiid1\":\"BS12082647\",\"start_time\":\"\",\"spell\":\"cheng xiang\",\"time_desc_json\":\"\",\"station_id\":\"900000086395001\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.492531;43.497791\",\"poiid2_xy\":\"123.492494;43.497809\",\"poiid2\":\"BV10941234\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"美林家居\",\"station_num\":\"2\",\"poiid1\":\"BS12083205\",\"start_time\":\"\",\"spell\":\"mei lin jia ju\",\"time_desc_json\":\"\",\"station_id\":\"900000086395003\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.492775;43.503876\",\"poiid2_xy\":\"123.492592;43.503712\",\"poiid2\":\"BV10941236\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"卫校\",\"station_num\":\"3\",\"poiid1\":\"BS12083194\",\"start_time\":\"\",\"spell\":\"wei xiao\",\"time_desc_json\":\"\",\"station_id\":\"900000086395004\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.494469;43.505432\",\"poiid2_xy\":\"123.493507;43.504574\",\"poiid2\":\"BV10941238\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"四小\",\"station_num\":\"4\",\"poiid1\":\"BS12083213\",\"start_time\":\"\",\"spell\":\"si xiao\",\"time_desc_json\":\"\",\"station_id\":\"900000086395005\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.495422;43.506351\",\"poiid2_xy\":\"123.494774;43.505711\",\"poiid2\":\"BV10941251\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"五中\",\"station_num\":\"5\",\"poiid1\":\"BS12083190\",\"start_time\":\"\",\"spell\":\"wu zhong\",\"time_desc_json\":\"\",\"station_id\":\"900000086395006\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.499321;43.506622\",\"poiid2_xy\":\"123.501076;43.504101\",\"poiid2\":\"BV10941138\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"实验小学\",\"station_num\":\"6\",\"poiid1\":\"BS12083199\",\"start_time\":\"\",\"spell\":\"shi yan xiao xue\",\"time_desc_json\":\"\",\"station_id\":\"900000086395007\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.503807;43.506714\",\"poiid2_xy\":\"123.503807;43.506714\",\"poiid2\":\"BV10942684\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"烟草\",\"station_num\":\"7\",\"poiid1\":\"BS12083196\",\"start_time\":\"\",\"spell\":\"yan cao\",\"time_desc_json\":\"\",\"station_id\":\"900000086395008\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.507912;43.506802\",\"poiid2_xy\":\"123.507912;43.506802\",\"poiid2\":\"BV10942683\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"大市场\",\"station_num\":\"8\",\"poiid1\":\"BS12083195\",\"start_time\":\"\",\"spell\":\"da shi chang\",\"time_desc_json\":\"\",\"station_id\":\"900000086395009\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.512245;43.507698\",\"poiid2_xy\":\"123.512154;43.508034\",\"poiid2\":\"BV10941239\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"双胜种业\",\"station_num\":\"9\",\"poiid1\":\"BS12083206\",\"start_time\":\"\",\"spell\":\"shuang sheng zhong ye\",\"time_desc_json\":\"\",\"station_id\":\"900000086395010\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.512276;43.509827\",\"poiid2_xy\":\"123.512169;43.509628\",\"poiid2\":\"BV10941143\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"水沐年华洗浴\",\"station_num\":\"10\",\"poiid1\":\"BS12083218\",\"start_time\":\"\",\"spell\":\"shui mu nian hua xi yu\",\"time_desc_json\":\"\",\"station_id\":\"900000086395011\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.507378;43.51049\",\"poiid2_xy\":\"123.508232;43.510372\",\"poiid2\":\"BV10941237\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"华生电器\",\"station_num\":\"11\",\"poiid1\":\"BS12083217\",\"start_time\":\"\",\"spell\":\"hua sheng dian qi\",\"time_desc_json\":\"\",\"station_id\":\"900000086395012\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.507118;43.511719\",\"poiid2_xy\":\"123.507004;43.511959\",\"poiid2\":\"BV10941252\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"林业局\",\"station_num\":\"12\",\"poiid1\":\"BS12083207\",\"start_time\":\"\",\"spell\":\"lin ye ju\",\"time_desc_json\":\"\",\"station_id\":\"900000086395013\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.506981;43.515465\",\"poiid2_xy\":\"123.508388;43.515851\",\"poiid2\":\"BV10942418\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"城建小区\",\"station_num\":\"13\",\"poiid1\":\"BS12083193\",\"start_time\":\"\",\"spell\":\"cheng jian xiao qu\",\"time_desc_json\":\"\",\"station_id\":\"900000086395014\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.510971;43.516029\",\"poiid2_xy\":\"123.510971;43.516029\",\"poiid2\":\"BV10942682\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"物资局\",\"station_num\":\"14\",\"poiid1\":\"BS12083189\",\"start_time\":\"\",\"spell\":\"wu zi ju\",\"time_desc_json\":\"\",\"station_id\":\"900000086395015\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.512299;43.516243\",\"poiid2_xy\":\"123.511528;43.518734\",\"poiid2\":\"BV10941135\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"中心医院\",\"station_num\":\"15\",\"poiid1\":\"BS12080035\",\"start_time\":\"\",\"spell\":\"zhong xin yi yuan\",\"time_desc_json\":\"\",\"station_id\":\"900000086395016\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.510941;43.521301\",\"poiid2_xy\":\"123.509590;43.519985\",\"poiid2\":\"BV10941097\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"市幼儿园\",\"station_num\":\"16\",\"poiid1\":\"BS12080052\",\"start_time\":\"\",\"spell\":\"shi you er yuan\",\"time_desc_json\":\"\",\"station_id\":\"900000086395017\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.510078;43.52309\",\"poiid2_xy\":\"123.509254;43.523029\",\"poiid2\":\"BV10941141\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"瑞馨佳苑\",\"station_num\":\"17\",\"poiid1\":\"BS12083197\",\"start_time\":\"\",\"spell\":\"rui xin jia yuan\",\"time_desc_json\":\"\",\"station_id\":\"900000086395018\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.506554;43.522491\",\"poiid2_xy\":\"123.506554;43.522491\",\"poiid2\":\"BV10941133\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"白市街\",\"station_num\":\"18\",\"poiid1\":\"BS12083188\",\"start_time\":\"\",\"spell\":\"bai shi jie\",\"time_desc_json\":\"\",\"station_id\":\"900000086395019\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.503845;43.523472\",\"poiid2_xy\":\"123.503845;43.523472\",\"poiid2\":\"BV10942680\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"东丰\",\"station_num\":\"19\",\"poiid1\":\"BS12083202\",\"start_time\":\"\",\"spell\":\"dong feng\",\"time_desc_json\":\"\",\"station_id\":\"900000086395020\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.501091;43.527679\",\"poiid2_xy\":\"123.501091;43.527679\",\"poiid2\":\"BV10942685\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"东丰五队\",\"station_num\":\"20\",\"poiid1\":\"BS12083209\",\"start_time\":\"\",\"spell\":\"dong feng wu dui\",\"time_desc_json\":\"\",\"station_id\":\"900000086395021\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.492027;43.530674\",\"poiid2_xy\":\"123.492027;43.530674\",\"poiid2\":\"BV10942687\"},{\"status\":\"1\",\"trans_flag\":\"\",\"code\":\"220382\",\"navi_poiid\":\"\",\"name\":\"电厂转盘\",\"station_num\":\"21\",\"poiid1\":\"BS12079844\",\"start_time\":\"\",\"spell\":\"dian chang zhuan pan\",\"time_desc_json\":\"\",\"station_id\":\"900000086395022\",\"navi_poiname\":\"\",\"end_time\":\"\",\"navi_poiside\":\"\",\"xy_coords\":\"123.479065;43.527317\",\"poiid2_xy\":\"123.479218;43.527351\",\"poiid2\":\"BV10941066\"}]}";
        JSONObject jsonObject = JSON.parseObject(str);
        List<JSONObject> list = jsonObject.getJSONArray("stations").toJavaList(JSONObject.class);
        for (JSONObject json : list) {
            String stationName = json.getString("name");
            String stationCode = json.getString("station_id");
            String[] lonlat = json.getString("xy_coords").split(";");
            String gpsX = lonlat[0];
            String gpsY = lonlat[1];
            String stationSql = String.format("insert into bus_station (STATION_CODE, STATION_NAME, LONGITUDE, LATITUDE) values('%s','%s','%s','%s');", stationCode, stationName, gpsX, gpsY);
            System.out.println(stationSql);
        }

        System.out.println("");
        int idx1 = 1;
        List<String> stationCodeList = Lists.newArrayList();
        for (JSONObject json : list) {
            String stationCode = json.getString("station_id");
            stationCodeList.add(stationCode);
            String lineSql = String.format("insert into line_bus_station_rel (LINE_CODE, LINE_TYPE, STATION_CODE, IDX) values('%s',1,'%s','%s');", lineCode, stationCode, idx1);
            System.out.println(lineSql);
            idx1 ++;
        }

        int idx2 = 1;
        for (int i = stationCodeList.size()-1; i>=0; i--) {
            String stationCode = stationCodeList.get(i);
            String lineSql = String.format("insert into line_bus_station_rel (LINE_CODE, LINE_TYPE, STATION_CODE, IDX) values('%s',2,'%s','%s');", lineCode, stationCode, idx2);
            System.out.println(lineSql);
            idx2++;
        }




    }

    public static void main(String[] args) {
        String str1 = "吉c69405,\n" +
                "吉c69400,\n" +
                "吉c69383,\n" +
                "吉c69343,\n" +
                "吉c69391,\n" +
                "吉c69370,\n" +
                "吉c69331,\n" +
                "吉c69300,\n" +
                "吉c69328,\n" +
                "吉c69335,\n" +
                "吉c69329,\n" +
                "吉c69337,\n" +
                "吉c69336,\n" +
                "吉c69339,\n" +
                "吉C69251,\n" +
                "吉C69338,\n" +
                "吉C69369,\n" +
                "吉C69398,\n" +
                "吉C69399,\n" +
                "吉C6A109,\n" +
                "吉C6A113,\n" +
                "吉C6A101,\n" +
                "吉C6A125,\n" +
                "吉C6A131,\n" +
                "吉C6A136,\n" +
                "吉C6A137,\n" +
                "吉C6A138,\n" +
                "吉C6A336,\n" +
                "吉C6A338,\n" +
                "吉C6A595,\n" +
                "吉C6A707,\n" +
                "吉C69387,\n" +
                "吉C6A102,\n" +
                "吉C6A103,\n" +
                "吉C6A105,\n" +
                "吉C6A106,\n" +
                "吉C6A112,\n" +
                "吉C6A115,\n" +
                "吉C6A117,\n" +
                "吉C6A120,\n" +
                "吉C6A126,\n" +
                "吉C6A127,\n" +
                "吉C6A128,\n" +
                "吉C6A129,\n" +
                "吉C6A130,\n" +
                "吉C6A132,\n" +
                "吉C6A135,\n" +
                "吉C6A139,\n" +
                "吉C6A150,\n" +
                "吉C6A151,\n" +
                "吉C6A152,\n" +
                "吉C6A505,\n" +
                "吉C6A575,\n" +
                "吉C6A757,\n" +
                "吉C69137,\n" +
                "吉C69210,\n" +
                "吉C69213,\n" +
                "吉C69230,\n" +
                "吉C69231,\n" +
                "吉C69245,\n" +
                "吉C69250,\n" +
                "吉C69253,\n" +
                "吉C69273,\n" +
                "吉C69283,\n" +
                "吉C69295,\n" +
                "吉C69305,\n" +
                "吉C69310,\n" +
                "吉C69312,\n" +
                "吉C69313,\n" +
                "吉C69317,\n" +
                "吉C69318,\n" +
                "吉C69319,\n" +
                "吉C69320,\n" +
                "吉C69327,\n" +
                "吉C69340,\n" +
                "吉C69341,\n" +
                "吉C69342,\n" +
                "吉C69350,\n" +
                "吉C69353,\n" +
                "吉C69356,\n" +
                "吉C69357,\n" +
                "吉C69358,\n" +
                "吉C69359,\n" +
                "吉C69360,\n" +
                "吉C69363,\n" +
                "吉C69365,\n" +
                "吉C69366,\n" +
                "吉C69367,\n" +
                "吉C69368,\n" +
                "吉C69375,\n" +
                "吉C69377,\n" +
                "吉C69380,\n" +
                "吉C69381,\n" +
                "吉C69382";
        String str2 = "吉c69377,\n" +
                "吉c69342,\n" +
                "吉c69340,\n" +
                "吉c69368,\n" +
                "吉c69210,\n" +
                "吉c69357,\n" +
                "吉C6A595,\n" +
                "吉C6A707,\n" +
                "吉C6A757,\n" +
                "吉c69253,\n" +
                "吉c69313,\n" +
                "吉c69310,\n" +
                "吉c69245,\n" +
                "吉c69353,\n" +
                "吉c69318,\n" +
                "吉c69213,\n" +
                "吉c69382,\n" +
                "吉c69366,\n" +
                "吉c69358,\n" +
                "吉c69305,\n" +
                "吉c69367,\n" +
                "吉c69273,\n" +
                "吉c69250,\n" +
                "吉c69341,\n" +
                "吉c69230,\n" +
                "吉c69312,\n" +
                "吉c69350,\n" +
                "吉c69356,\n" +
                "吉c69365,\n" +
                "吉c69327,\n" +
                "吉c69359,\n" +
                "吉c69317,\n" +
                "吉c69360,\n" +
                "吉c69338,\n" +
                "吉c69369,\n" +
                "吉c69387,\n" +
                "吉c69398,\n" +
                "吉c69399,\n" +
                "吉C6A101,\n" +
                "吉c69405,\n" +
                "吉c69400,\n" +
                "吉c69383,\n" +
                "吉c69343,\n" +
                "吉c69391,\n" +
                "吉c69370,\n" +
                "吉c69331,\n" +
                "吉c69300,\n" +
                "吉c69328,\n" +
                "吉c69335,\n" +
                "吉c69329,\n" +
                "吉c69337,\n" +
                "吉c69336,\n" +
                "吉c69339";

        List<String> list1 = Stream.of(str1.replaceAll("\n", "").split(",")).collect(Collectors.toList());
        List<String> list2 = Stream.of(str2.replaceAll("\n", "").split(",")).collect(Collectors.toList());

        System.out.println(list1.size());
        System.out.println("--------------------------------");
        System.out.println(list2.size());
        list1.removeAll(list2);
        System.out.println(list1.size());

    }

}
