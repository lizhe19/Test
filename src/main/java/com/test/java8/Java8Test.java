package com.test.java8;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang.CharSet;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;


/**
 * 测试类
 *
 * @author lz-119612
 * @version 1.0
 * @date 2019/10/24 14:09
 **/
public class Java8Test {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Bean {
        private Integer page;
        private Integer pageSize;
    }



    public static void main1(String[] args) {
        Map<String, Integer> map1 = Maps.newHashMap();
        map1.put("key1", 1);
        map1.put("key2", 2);
        Map<String, Integer> map2 = Maps.newHashMap();
        map2.put("key1", 11);
        map2.put("key2", 22);
        List<Map<String, Integer>> mapList = Lists.newArrayList();
        mapList.add(map1);
        mapList.add(map2);

        System.out.println((Integer) mapList.stream().mapToInt(o -> o.get("key1")).sum());
        System.out.println(mapList.stream().collect(Collectors.summingInt(o -> o.get("key1"))));

        Optional<Map<String, Integer>> optional = mapList.stream().max(Comparator.comparing(o -> o.get("key2")));
        optional.ifPresent(o -> System.out.println(o.get("key2")));
        System.out.println(mapList.stream().map(o -> o.get("key2")).max(Comparator.comparing(o -> o)).orElse(0));



        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        System.out.println(Collections.binarySearch(names, "mike"));
        Collections.sort(names, Collections.reverseOrder());
        System.out.println("----"+names);
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });
        Collections.sort(names, (a, b) -> b.compareTo(a));

        Collections.sort(names, Comparator.reverseOrder());

        names.sort(Comparator.reverseOrder());
        System.out.println(names);
        names.sort(Comparator.comparing(o -> o));
        System.out.println(names);
        List<Integer> list = Lists.newArrayList(1,7,4);
        System.out.println(list.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()));
        System.out.println(list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
        System.out.println(list.stream().sorted(Comparator.comparing(o -> (int)o).reversed()).collect(Collectors.toList()));

        System.out.println(list.stream().count());
        System.out.println(list.stream().max(Comparator.comparing(o -> o)).orElse(0));
        System.out.println(list.stream().min(Comparator.comparing(o -> o)).orElse(0));
        System.out.println(list.stream().collect(Collectors.summingInt(o -> o)));
        System.out.println((Integer) list.stream().mapToInt(o -> o).sum());

        System.out.println(list.stream().collect(Collectors.groupingBy(o -> o)));
        System.out.println(list.stream().collect(Collectors.groupingBy(o -> o, Collectors.summingInt(o -> o))));
        System.out.println(list.stream().collect(Collectors.groupingBy(o -> o, Collectors.counting())));
        System.out.println(list.stream().collect(Collectors.toMap(o -> o, o -> o, (a,b) -> a)));


        String[] strArr = new String[]{"1", "2"};
        System.out.println(Arrays.toString(strArr));


        //list转String用指定符号连接
        List<String> testList = Lists.newArrayList("lz", "chen", "zhe");
        System.out.println(StringUtils.join(testList, ","));
        System.out.println(String.join(",", testList));
        System.out.println(testList.stream().collect(Collectors.joining(",")));


    }


    @Data
    private static class Bean1 {
        private Long id;
        private String name;
        private Integer age;
    }
    @Data
    private static class Bean2 {
        private Long id;
        private String nameCopy;
        private Integer age;
    }
    private static final Function<Bean1, Bean2> bean1ToBean2 = bean1 -> {
        Bean2 beanCopy = new Bean2();
        BeanUtils.copyProperties(bean1, beanCopy);
        beanCopy.setNameCopy(bean1.getName());
        return beanCopy;
    };

    private static final Consumer<String> consumer = param -> {
        System.out.println("consumer print: " + param);
    };

    private static final Predicate<String> predicate =  param -> {
        return "value".equals(param);
    };

    public static void main2(String[] args) throws ExecutionException {


        Bean1 bean1 = new Bean1();
        bean1.setId(1L);
        bean1.setName("name1");
        Bean1 bean2 = new Bean1();
        bean2.setId(2L);
        bean2.setName("name2");
        List<Bean1> list = Lists.newArrayList();
        list.add(bean1);
        list.add(bean2);
        List<Bean2> resultList = list.stream().map(bean1ToBean2).collect(Collectors.toList());

        System.out.println(JSON.toJSONString(resultList));
        Bean2 bean = bean1ToBean2.apply(bean1);
        System.out.println(JSON.toJSONString(bean));

        Supplier<Boolean> supplier = () -> {
            try {
                //TODO
                System.out.println("operate over!");
            } catch (Exception e) {
                return false;
            }
            return true;
        };

        System.out.println("***********" + method1(supplier));

        consumer.accept("value");

        System.out.println(predicate.test("value"));

        //自定义函数式接口
        BooleanFunctionalInterface functionalInterface = new BooleanFunctionalInterface() {
            @Override
            public Boolean exec(Object o) {
                return true;
            }
        };

        System.out.println("***********"+functionalInterface.exec(1));

    }

    private static final Lock lock = new ReentrantLock();

    private static <T> T method1(Supplier<T> supplier) {
        lock.lock();
        T result = supplier.get();
        lock.unlock();
        return result;

    }

    public static void main3(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        System.out.println(longAdder.intValue());
        longAdder.add(10);
        System.out.println(longAdder.intValue());
        longAdder.add(11);
        longAdder.decrement();
        longAdder.add(-1);
        System.out.println(longAdder.sum());
        longAdder.sumThenReset();
        System.out.println(longAdder.sum());

        List<String> list = Lists.newArrayList("value1", "value2", "value2");
        System.out.println(StringUtils.join(list, ","));

        //连接字符 结果如value1,value2
        StringJoiner stringJoiner1 = new StringJoiner(",");
        //依次连接字符、前置字符、后置字符串  结果如[value1,value2]
        //StringJoiner stringJoiner2 = new StringJoiner(",", "[", "]");
        stringJoiner1.add("value1").add("value2");
        System.out.println(stringJoiner1.toString());
    }


    /**
     * 将接口当参数传入的一种写法 达到延迟执行的作用  同Java8 例如Supplier,Function,Consumer,Predicate等函数式接口
     */
    public interface TestInterface {
        void method1();
    }

    public static void main4(String[] args) {
        haTestMethod(testInterface);
        haTestMethod(new TestInterfaceImpl());
    }

    public static void haTestMethod(TestInterface testInterface) {
        testInterface.method1();
    }

    //直接封装接口
    static TestInterface testInterface = new TestInterface() {
        @Override
        public void method1() {
            System.out.println("come in testInterface");
        }
    };
    //或者封装为实现类
    static class TestInterfaceImpl implements TestInterface {
        @Override
        public void method1() {
            System.out.println("come in testInterfaceImpl");
        }
    }


    public static void main456(String[] args) {
        System.out.println(Lists.newArrayList(3, 2, 1).stream().reduce(Integer::sum).orElse(0));
        System.out.println(Lists.newArrayList(3, 2, 1).stream().reduce((a, b) -> Integer.sum(a, b)).orElse(0));
        System.out.println((Integer) Lists.newArrayList(3, 2, 1).stream().mapToInt(o -> o).sum());
        System.out.println(Lists.newArrayList(3, 2, 1).stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()));
        Long sum = LongStream.range(1L, 10L).reduce(0, Long::sum);
        System.out.println(JSON.toJSONString(LongStream.range(1L, 10L).toArray()));
        System.out.println(sum);
        System.out.println(new Random().nextInt(10) + "------" + RandomStringUtils.randomNumeric(4));

    }

    public static void main666(String[] args) {
        List<String> list = Lists.newArrayList("aaa", "bbb", "bbb");
        if (list.stream().anyMatch(o -> o.equals("bbb"))) {
            System.out.println(list);
        }
        if (list.stream().allMatch(o -> o.equals("bbb"))) {
            System.out.println(list);
        }
        if (list.stream().noneMatch(o -> o.equals("ccc"))) {
            System.out.println(list);
        }
        //findAny()操作，返回的元素是不确定的 尤其是并发情况下
        System.out.println(list.stream().findAny().orElse("dd"));
        //findFirst()操作，返回的元素是第一个
        System.out.println(list.stream().findFirst().orElse("dd"));

        //flap
        List<String> list2 = list.stream().flatMap(o -> Stream.of(o.split(""))).distinct().collect(Collectors.toList());
        System.out.println(list2);

        List<String> lists = Lists.newArrayList("1");
        List list3 = Optional.of(lists).get();
        System.out.println(list3);

        //of与ofNullable的区别  of的参数必须是非空的  ofNullable参数如果为空 则返回一个空的optional  对空的optional可以用ifPresent或者orElse解决
        Optional optional = Optional.ofNullable(lists);
        optional.ifPresent(o -> System.out.println(o));


        System.out.println(StringUtils.join(list,";"));

        //flatMap
        listHB();

    }


    private static void listHB() {
        List<List<String>> list = Arrays.asList(Arrays.asList("aaa", "aaa1"), Arrays.asList("bbb", "bbb1"), Arrays.asList("ccc", "ccc1"));
        List<String> result = list.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(result);

    }

    public static void main23211(String[] args) {
        System.out.println(new Random().nextInt(10) + "------" + RandomStringUtils.randomNumeric(4)+"-----"+RandomUtils.nextInt(10));
        List<Map<String, String>> cameraInfos = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("aaaa", "value1");
        map.put("bbbb", "value1");

        Map<String, String> map1 = Maps.newHashMap();
        map1.put("aaaa", "value2");
        map1.put("bbbb", "value2");

        Map<String, String> map2 = Maps.newHashMap();
        map2.put("aaaa", "value1");
        map2.put("bbbb", "value3");

        cameraInfos.add(map);
        cameraInfos.add(map1);
        cameraInfos.add(map2);

        System.out.println("-------"+ cameraInfos);

        List<Map<String, String>> errorList = cameraInfos.stream().filter( o -> o.get("aaaa").equals("value2")).collect(Collectors.toList());
        cameraInfos.removeAll(errorList);

        System.out.println(cameraInfos);

        //peek 与 map的区别 ，peek内部是consumer map是function
        List<Map<String, String>> result = cameraInfos.stream().peek(o -> o.put("cccc", "value4")).collect(Collectors.toList());

        System.out.println(result);

        //flatMap
        List<String> list1 = Lists.newArrayList("aaa1", "bbb1");
        List<String> list2 = Lists.newArrayList("aaa2", "bbb2");
        List<String> list3 = Lists.newArrayList("aaa3", "bbb3");
        List<String> listAll = Stream.of(list1, list2, list3).flatMap(o -> o.stream()).collect(Collectors.toList());
        System.out.println(listAll);

        String[] arr1 = new String[]{"aaa1", "bbb1"};
        String[] arr2 = {"aaa2", "bbb2"};
        String[] arr3 = {"aaa3", "bbb3"};
        List<String> listAll1 = Stream.of(arr1, arr2, arr3).flatMap(Stream::of).collect(Collectors.toList());
        System.out.println(listAll1);


    }

    public static void main909(String[] args) throws UnsupportedEncodingException {
        StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
        System.out.println(stringJoiner.toString());
        System.out.println("password");

        String str = null;
        Optional.ofNullable(str).ifPresent(System.out::println);
        System.out.println(Optional.ofNullable(str).orElse("sds"));
        Optional.ofNullable(str).orElseThrow(RuntimeException::new);
    }


    private static List<Integer> getOriginalObjIdList(List<ModelTemplateObject> modelTemplateObjects) {
        List<Integer> surplusList = Lists.newArrayList();
        List<Integer> allList = modelTemplateObjects.stream().map(ModelTemplateObject::getId).collect(Collectors.toList());
        List<ModelTemplateObject> modelTemplateObjectsCopy = modelTemplateObjects;
        allList.forEach(id -> {
            modelTemplateObjectsCopy.forEach(o -> {
                if (o.getInherit().contains(String.valueOf(id))) {
                    surplusList.add(o.getId());
                }
            });
        });
        allList.removeAll(surplusList);
        return allList;
    }

    public static void main(String[] args) {
        ModelTemplateObject obj1 = new ModelTemplateObject().setId(30).setInherit("[20]");
        ModelTemplateObject obj2 = new ModelTemplateObject().setId(36).setInherit("[20]");
        ModelTemplateObject obj3 = new ModelTemplateObject().setId(40).setInherit("[20]");
        ModelTemplateObject obj4 = new ModelTemplateObject().setId(44).setInherit("[14]");
        ModelTemplateObject obj5 = new ModelTemplateObject().setId(58).setInherit("[20]");

        ModelTemplateObject obj6 = new ModelTemplateObject().setId(96).setInherit("[20,30]");
        ModelTemplateObject obj7 = new ModelTemplateObject().setId(98).setInherit("[20,36]");
        ModelTemplateObject obj8 = new ModelTemplateObject().setId(100).setInherit("[20,40]");
        ModelTemplateObject obj9 = new ModelTemplateObject().setId(102).setInherit("[20,44]");
        ModelTemplateObject obj10 = new ModelTemplateObject().setId(104).setInherit("[18]");
        ModelTemplateObject obj11 = new ModelTemplateObject().setId(106).setInherit("[18,104]");
        ModelTemplateObject obj12 = new ModelTemplateObject().setId(108).setInherit("[18]");
        ModelTemplateObject obj13 = new ModelTemplateObject().setId(110).setInherit("[18,108]");
        List<ModelTemplateObject> list = Lists.newArrayList(obj1, obj2, obj3, obj4,obj5,obj6,obj7,obj8,obj9,obj10,obj11,obj12,obj13);
        System.out.println(getOriginalObjIdList(list));


    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("model_template_object")
    @ApiModel(value = "ModelTemplateObject对象", description = "自定义模板对象表")
    public static class ModelTemplateObject extends Model<ModelTemplateObject> {

        private static final long serialVersionUID = 1L;

        public ModelTemplateObject() {
        }

        public ModelTemplateObject(Integer parentId, Integer templateId, String browseName, String displayName, String description) {
            this.parentId = parentId;
            this.templateId = templateId;
            this.browseName = browseName;
            this.displayName = displayName;
            this.description = description;
        }

        public ModelTemplateObject(Integer parentId, Integer templateId, String browseName, String displayName, String description, Integer originalType, String inherit, Integer delEnable) {
            this.parentId = parentId;
            this.templateId = templateId;
            this.browseName = browseName;
            this.displayName = displayName;
            this.description = description;
            this.originalType = originalType;
            this.inherit = inherit;
            this.delEnable = delEnable;
        }

        public ModelTemplateObject(Integer parentId, String path, Integer templateId, String browseName, String displayName,
                                   String description, Integer originalType
        ) {
            this.parentId = parentId;
            this.path = path;
            this.templateId = templateId;
            this.browseName = browseName;
            this.displayName = displayName;
            this.description = description;
            this.originalType = originalType;
        }

        public ModelTemplateObject(Integer parentId, String path, Integer templateId, String browseName, String displayName, String description, Integer originalType, Integer delEnable) {
            this.parentId = parentId;
            this.path = path;
            this.templateId = templateId;
            this.browseName = browseName;
            this.displayName = displayName;
            this.description = description;
            this.originalType = originalType;
            this.delEnable = delEnable;
        }

        public ModelTemplateObject(Integer parentId, String path, Integer templateId, String browseName, String displayName, String description, Integer originalType,
                                   String inherit, Integer delEnable
        ) {
            this.parentId = parentId;
            this.path = path;
            this.templateId = templateId;
            this.browseName = browseName;
            this.displayName = displayName;
            this.description = description;
            this.originalType = originalType;
            this.inherit = inherit;
//        this.nodeId = nodeId;
            this.delEnable = delEnable;
        }

        public ModelTemplateObject(ModelTemplateObject other) {
            this.parentId = other.parentId;
            this.path = other.path;
            this.templateId = other.templateId;
            this.browseName = other.browseName;
            this.displayName = other.displayName;
            this.description = other.description;
            this.originalType = other.originalType;
            this.inherit = other.inherit;
//        this.nodeId = other.nodeId;
            this.delEnable = other.delEnable;
        }

        @ApiModelProperty(value = "主键id")
        @TableId(value = "id", type = IdType.AUTO)
        private Integer id;

        @ApiModelProperty(value = "父节点Id")
        @TableField("parentId")
        private Integer parentId;
        /**
         * 父节点路径
         */
        @ApiModelProperty(value = "父节点路径")
        private String path;
        @ApiModelProperty(value = "父模板Id")
        @TableField("templateId")
        private Integer templateId;

        @ApiModelProperty(value = "浏览名")
        @TableField("browseName")
        private String browseName;

        @ApiModelProperty(value = "显示名")
        @TableField("displayName")
        private String displayName;

        @ApiModelProperty(value = "描述")
        @TableField("description")
        private String description;

        @ApiModelProperty(value = "原始模板类型，为枚举值")
        @TableField("originalType")
        private Integer originalType;

        @ApiModelProperty(value = "完整派生关系列表，如[1,12,123]")
        @TableField("inherit")
        private String inherit;
//    @ApiModelProperty(value = "模板类型，0为系统模板，1为应用模板,2为自定义模板")
//    @TableField("type")
//    private Integer type;
        /**
         * NodeId
         */
//    @ApiModelProperty(value="NodeId")
//    private String nodeId;

        /**
         * 是否可删除/是否可编辑基本信息
         */
        @ApiModelProperty(value = "是否可删除/是否可编辑基本信息")
        private Integer delEnable;

        @Override
        protected Serializable pkVal() {
            return this.id;
        }

    }
}
