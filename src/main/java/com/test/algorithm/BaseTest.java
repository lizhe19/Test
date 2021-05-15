package com.test.algorithm;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lz-119612
 * @version 1.0
 * @date 2021/1/27 17:23
 *  
 **/
public class BaseTest {

    static Scanner sc = new Scanner(System.in);

    //翻转字符串
    public static void main1(String[] args) {
        System.out.print("输入字符串:");
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();
        System.out.println(new StringBuilder(value).reverse().toString());
        System.out.println("---------------");
        StringJoiner join = new StringJoiner("", "[", "]");
        for (int i=value.length()-1;i>0;i--) {
            join.add(value.charAt(i)+"");
        }
        System.out.println(join.toString());
        scanner.close();
        /*int value;
        while (scanner.hasNextInt()) {
            value = scanner.nextInt();
            if (1 == value) {
                break;
            }
            System.out.println(value);
            scanner.close();
        }*/
    }
    //输入字符串灭个字符词频
    public static void main2(String[] args) {
        System.out.print("输入字符串:");
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();

        Map<String, Long> map = Arrays.stream(value.split("")).collect(Collectors.groupingBy(o -> o, Collectors.counting()));
        System.out.println(JSON.toJSONString(map));
        /*for (int i=0; i<value.length();i++) {

        }*/
    }
    //十进制转16机制
    public static void main3(String[] args) {
        System.out.print("输入数字1:");
        Scanner sc = new Scanner(System.in);
        int value = sc.nextInt();
        String hexValue = Integer.toHexString(value);
        System.out.println(hexValue);
    }
    //16进制转十进制
    public static void main4(String[] args) {
        Scanner sc = new Scanner(System.in);
        String value = sc.nextLine().substring(2);
        System.out.println("value="+value);
        System.out.println(Integer.valueOf(value, 16));
    }

    //质数因子
    public static void main5(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int i = 2;
            StringBuilder str = new StringBuilder();
            int value = sc.nextInt();
            while (value>1) {
                while (value % i == 0) {
                    str.append(i).append(" ");
                    value = value/i;
                }
                i++;
            }
            System.out.println(str.toString());
        }
    }

    //取近似值
    public static void main6(String[] args) {
        while (sc.hasNext()) {
            float value = sc.nextFloat();
            float temp = value - (int)value;
            int result = (int)value;
            if (temp >= 0.5) {
                result = (int)value + 1;
            }
            System.out.println(result);
        }
    }

    //合并表记录["0 1", "0 2", "1 2"]
    public static void main7(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = Integer.parseInt(sc.nextLine());
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i<num; i++) {
            String[] valueArr = sc.nextLine().split(" ");
            if (null == map.get(valueArr[0])) {
                map.put(valueArr[0], Integer.valueOf(valueArr[1]));
            } else {
                map.put(valueArr[0], map.get(valueArr[0]) + Integer.parseInt(valueArr[1]));
            }
        }
        map.forEach((key, value) -> {
            System.out.println(key+" "+value);
        });
    }

    //输入整数的二进制 包含1的个数
    public static void main8(String[] args) {
        int value = sc.nextInt();
        String valueStr = Integer.toBinaryString(value);
        System.out.println("valueStr="+valueStr);
        char[] chars = valueStr.toCharArray();
        int count = 0;
        for (char cha : chars) {
            if (cha == '1') {
                count++;
            }
        }
        System.out.println(count);
        System.out.println(valueStr.length()- valueStr.replace("1", "").length());
    }

    //坐标移动
    public static void main9(String[] args) {
        while (sc.hasNext()) {
            String value = sc.nextLine();
            String[] arr = value.split(";");
            Point point = new Point();
            for (String str : arr) {
                if (str.length()>1 && str.length()<=3) {
                    char[] chars = str.toCharArray();
                    if (chars[0] == 'A' || chars[0] == 'W' || chars[0] == 'S' || chars[0] == 'D') {
                        if (str.substring(1).length() == str.replaceAll("[^0-9]", "").length()) {
                            direction(str, point);
                        }

                    }
                }
            }
            System.out.println(point.x + "," + point.y);
        }
    }

    private static void direction(String str, Point start) {
        if (str.charAt(0) == 'A') {
            start.x = start.x - Integer.parseInt(str.substring(1));
        }
        if (str.charAt(0) == 'W') {
            start.y = start.y + Integer.parseInt(str.substring(1));
        }
        if (str.charAt(0) == 'S') {
            start.y = start.y - Integer.parseInt(str.substring(1));
        }
        if (str.charAt(0) == 'D') {
            start.x = start.x + Integer.parseInt(str.substring(1));
        }
    }

    static class Point {
        int x;
        int y;
    }

    //密码校验
    public static void main10(String[] args) {
        while (sc.hasNext()) {
            boolean result;
            String value = sc.nextLine();
            //长度大于8
            if (null == value || value.length()<8) {
                result = false;
            } else {
                result = checkCharType(value) && checkRepeat(value);
            }
            System.out.println(result ? "OK" : "NG");
        }
    }
    //包含大写字母、小写字母、数字或其他至少3种
    private static boolean checkCharType(String value) {
        int upper = 0,lower=0,num=0,other=0;
        char[] chars = value.toCharArray();
        for (Character character : chars) {
            if (Character.isUpperCase(character)) {
                upper=1;
            } else if (Character.isLowerCase(character)) {
                lower=1;
            } else if (Character.isDigit(character)) {
                num=1;
            } else {
                other=1;
            }
        }
        int sum = upper+lower+num+other;
        return sum >= 3;
    }

    //检查重复2位字符串
    private static boolean checkRepeat(String value) {
        for (int i=0;i<value.length()-3;i++) {
            if (value.substring(i+3).contains(value.substring(i, i+3))) {
                return false;
            }
        }
        return true;
    }

    //简单密码破解
    public static void main11(String[] args) {
        String[] dic = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        List list = Arrays.asList(dic);
        Map<String, Integer> map = Maps.newHashMap();
        map.put("abc", 2);
        map.put("def", 3);
        map.put("ghi", 4);
        map.put("jkl", 5);
        map.put("mno", 6);
        map.put("pqrs", 7);
        map.put("tuv", 8);
        map.put("wxyz", 9);

        while (sc.hasNext()) {
            String value = sc.nextLine();
            char[] chars = value.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (Character character : chars) {
                if (Character.isUpperCase(character)) {
                    char charWord = Character.toLowerCase(character);
                    int index = list.indexOf(String.valueOf(charWord));
                    sb.append(dic[index+1]);
                } else if (Character.isLowerCase(character)) {
                    Set<String> set = map.keySet().stream().filter(o -> o.contains(String.valueOf(character))).collect(Collectors.toSet());
                    sb.append(map.get(set.iterator().next()));
                } else if (Character.isDigit(character)){
                    sb.append(character);
                } else {
                    System.out.println("error input");
                }
            }

            System.out.println(sb.toString());
        }

    }

    public static void main(String[] args) {



        /*while (sc.hasNext()) {
            int num = sc.nextInt();
            int count = 0;
            while (true) {
                int s1 = num/3;
                int s2 = num%3;
                count=count;
            }
        }*/
        System.out.println("A10a".replaceAll("[^0-9]", ""));
        System.out.println('a' - 'b');
        int[] arr = new int[] {5, 7, 1, 20, 15, 15, 7, 6};

        for (int i = 0;i<arr.length;i++) {
            for (int j=0;j<arr.length-i-1;j++) {
                if (arr[j]>arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        System.out.println(JSON.toJSONString(arr));

        String value = "adA cFhf";
        char[] chars = value.toCharArray();
        List<Character> list = Lists.newArrayList();
        for (char char1 : chars) {
            if (Character.isLetter(char1)) {
                list.add(Character.toLowerCase(char1));
            }
        }
        Character[] valueArr = new Character[list.size()];
        list.toArray(valueArr);
        for (int i = 0;i<valueArr.length;i++) {
            for (int j=0;j<valueArr.length-i-1;j++) {
                if (valueArr[j] - valueArr[j+1] > 0) {
                    Character temp = valueArr[j];
                    valueArr[j] = valueArr[j+1];
                    valueArr[j+1] = temp;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Character cc :valueArr) {
            sb.append(cc);
        }

        int i =0;
        for (char char2 : chars) {
            if (!Character.isLetter(char2)) {
                if (Character.isUpperCase(char2)) {
                    sb.append(Character.toUpperCase(valueArr[i]));
                } else {
                    sb.append(valueArr[i]);
                }
                i++;
            }
        }
        System.out.println(sb.toString());
    }
}
