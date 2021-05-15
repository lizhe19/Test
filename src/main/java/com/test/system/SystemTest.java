package com.test.system;

import com.alibaba.fastjson.JSON;

/**
 * @author lz-119612
 * @version 1.0
 * @date 2019/12/23 10:30
 **/
public class SystemTest {
    public static void main(String[] args) throws Exception {
        //System.getProperties()
        //System.out.println(System.getProperties());
        String os = System.getProperties().getProperty("os.name").toLowerCase();
        //isLinux = os.toLowerCase().indexOf("linux") > -1;
        System.out.println(os);
        System.out.println(os.toLowerCase().indexOf("linux"));

        java.net.InetAddress ia = java.net.InetAddress.getLocalHost();
        System.out.println(JSON.toJSONString(ia));
        System.out.println("ip:"+ia.getHostAddress());
        System.out.println("host name:"+ia.getHostName());

        //系统路径
        System.out.println(System.getProperty("user.dir"));
    }



    //    @Autowired
//    private  List<PublishService> publishServiceList;
//
//    @GetMapping("/test5")
//    public void publishTest() {
//        for (PublishService publishService : publishServiceList) {
//            if (publishService instanceof MessagePublishServiceImpl) {
//                System.out.println("MessagePublishServiceImpl");
//            } else if (publishService instanceof MailPublishServiceImpl) {
//                System.out.println("MailPublishServiceImpl");
//            }
//            publishService.publish();
//        }
//    }
}
