package com.test.algorithm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

/**
 * @author lz-119612
 * @version 1.0
 * @date 2020/8/21 8:59
 *  
 **/
public class DistanceCalculateTest {
    public static void main1(String[] args){

        GlobalCoordinates source = new GlobalCoordinates(43.524143, 123.478691);
        GlobalCoordinates target = new GlobalCoordinates(43.523136, 123.478905);

        double meter1 = getDistanceMeter(source, target, Ellipsoid.Sphere);
        double meter2 = getDistanceMeter(source, target, Ellipsoid.WGS84);

        System.out.println("Sphere坐标系计算结果："+meter1 + "米");
        System.out.println("WGS84坐标系计算结果："+meter2 + "米");

        System.out.println(new BigDecimal(126.312453).doubleValue());
        System.out.println(new BigDecimal(36.3).doubleValue());

        String str = "{\"capTime\": 1503709064010.0}";
        JSONObject json = JSON.parseObject(str);
        System.out.println("capTime: " + json.getDate("capTime"));
        LocalDateTime ldt = json.getDate("capTime").toInstant()
                .atZone(ZoneId.systemDefault() )
                .toLocalDateTime();
        System.out.println(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    }

    private static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid){

        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }

    public static void main2(String[] args) {
        TestDto dto1 = new TestDto(100, "name1", true);
        TestDto dto2 = new TestDto(101, "name2", true);
        TestDto dto3 = new TestDto(79, "name3", true);
        TestDto dto4 = new TestDto(99, "name4", true);
        TestDto dto5 = new TestDto(71, "name5", true);
        List<TestDto> list = Lists.newArrayList(dto1, dto2, dto3, dto4, dto5);

        TestDto result = list.stream().max(Comparator.comparing(TestDto::getNum)).orElse(null);
        System.out.println(JSON.toJSONString(result));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TestDto {
        private Integer num;
        private String name;
        private Boolean result;
    }

    public static void main3(String[] args) {
        List<TestDto> list1 = Lists.newArrayList(new TestDto(1, "name1", true), new TestDto(2, "name2", false),
                new TestDto(3, "name3", true));
        List<Boolean> list2 = Lists.newArrayList(true, false, true);
        for (int i = 0; i < list2.size(); i++) {
             list1.get(i).setResult(list2.get(i));
        }
        System.out.println(JSON.toJSONString(list1));

    }
}
