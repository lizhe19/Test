//package com.test.model;
//
//import com.dahua.big.traffic.transcode.manager.enums.SnapPictureOperationType;
//import com.dahua.big.traffic.transcode.manager.service.impl.SnapPictureTypeFtp;
//import com.dahua.big.traffic.transcode.manager.service.impl.SnapPictureTypeLocal;
//import com.dahua.big.traffic.transcode.manager.service.impl.SnapPictureTypeServer;
//import com.google.common.collect.Maps;
//
//import java.util.Map;
//
///**
// * 截图操作工厂
// *
// * @author lz-119612
// * @version 1.0
// * @date 2020/4/2 13:35
// **/
//public class SnapPictureFactory {
//    private static Map<String, SnapPictureOperation> map = Maps.newHashMap();
//    //如果有新的实现类 请同步缓存到这里
//    static {
//        //本地磁盘
//        map.put(SnapPictureOperationType.LOCAL_TYPE.getType(), new SnapPictureTypeLocal());
//        //图片服务器
//        map.put(SnapPictureOperationType.SERVER_TYPE.getType(), new SnapPictureTypeServer());
//        //ftp服务器
//        map.put(SnapPictureOperationType.FTP_TYPE.getType(), new SnapPictureTypeFtp());
//    }
//
//
//    /**
//     * 获取截图操作对象
//     * @param type 截图操作类型
//     */
//    public static SnapPictureOperation getSnapPictureOperate(String type) {
//        return map.get(type);
//    }
//}
