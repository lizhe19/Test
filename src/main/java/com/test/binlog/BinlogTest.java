package com.test.binlog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.google.code.or.OpenReplicator;
import com.google.code.or.binlog.BinlogEventListener;
import com.google.code.or.binlog.BinlogEventV4;
import com.google.code.or.binlog.impl.event.DeleteRowsEvent;
import com.google.code.or.binlog.impl.event.UpdateRowsEvent;
import com.google.code.or.binlog.impl.event.WriteRowsEvent;
import com.google.code.or.common.glossary.Column;
import com.google.code.or.common.glossary.Pair;
import com.google.code.or.common.glossary.Row;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * binlog测试
 * mysql的binlog设置类型[/etc/my.cnf]    statement  记录sql
 *                                      row  记录行详细信息
 *                                     mix 混合两种
 *
 * 解析binlog的工具有：mysql-binlog-connector-java
 *                    Open Replicator
 *                    ali canal
 *
 * @author lz-119612
 * @version 1.0
 * @date 2020/6/18 10:48
 *  
 **/
public class BinlogTest {
    private static final String host = "10.80.50.168";
    private static final Integer port = 3306;
    private static final String username = "root";
    private static final String password = "dahuacloud";


    public static void main(String[] args) {
        BinaryLogClient logClient = new BinaryLogClient(host, port, username, password);
        logClient.registerEventListener(event -> {
            EventData data = event.getData();
            if (null != data) {
                JSONObject json = JSONObject.parseObject(JSON.toJSONString(data));
                String database = json.getString("database");
                if ("highway_cloud".equals(database)) {
                    System.out.println("-----------data=" + JSON.toJSONString(data));
                    //如果日志是更新记录
                    if (data instanceof UpdateRowsEventData) {  //如果设置类型row
                        System.out.println("update");
                    } else if (data instanceof WriteRowsEventData) {
                        //写操作
                        System.out.println("write");
                    } else if (data instanceof DeleteRowsEventData) {
                        //删除操作
                        System.out.println("delete");
                    } else {
                        System.out.println("other");
                    }
                }
            }

        });

        try {
            logClient.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main1(String[] args) throws Exception {
        final OpenReplicator or = new OpenReplicator();
        or.setUser(username);
        or.setPassword(password);
        or.setHost(host);
        or.setPort(port);
        or.setServerId(23);
        or.setBinlogPosition(106);
        or.setBinlogFileName("mysql-bin.000001");

        or.setBinlogEventListener(new NotificationListener());
        or.start();
    }

    //获取数据库表结构
    public static void main2(String[] args) {
        ConnectionUtil.setConnection(host, port, username, password);
        Map<String, List<String>> columnsMap = ConnectionUtil.getColumns();
        System.out.println(JSON.toJSONString(columnsMap));
    }

    public static class NotificationListener implements BinlogEventListener {
        @Override
        public void onEvents(BinlogEventV4 event) {
            if (event == null) {
                System.out.println("binlog event is null");
                return;
            }

            if (event instanceof UpdateRowsEvent) {
                UpdateRowsEvent updateRowsEvent = (UpdateRowsEvent) event;
                //更新操作 拿到所有行
                List<Pair<Row>> rows = updateRowsEvent.getRows();
                List<Column> cols_after = null;
                List<Column> cols_before = null;
                for (Pair<Row> p : rows) {
                    //每行更新字段前的数据
                    Row after = p.getAfter();
                    Row before = p.getBefore();
                    //每行更新字段后的数据
                    cols_after = after.getColumns();
                    cols_before = before.getColumns();
                    break;
                }
                System.out.println("update event is:");
            } else if (event instanceof DeleteRowsEvent) {
                DeleteRowsEvent deleteRowsEvent = (DeleteRowsEvent) event;
                //删除的行的数据
                List<Row> rows = deleteRowsEvent.getRows();
                List<Column> before = null;
                for (Row row : rows) {
                    before = row.getColumns();
                    break;
                }
                System.out.println("delete event is:");

            } else if (event instanceof WriteRowsEvent) {
                WriteRowsEvent wrtiteRowsEvent = (WriteRowsEvent) event;
                //拿到所有行
                List<Row> rows = wrtiteRowsEvent.getRows();
                List<Column> before = null;
                for (Row row : rows) {
                    //拿到行的所有列
                    before = row.getColumns();
                    break;
                }
                System.out.println("write event is:");

            }
        }

    }


    public static class ConnectionUtil {

        private static Connection conn;

        private static String host;
        private static Integer port;
        private static String user;
        private static String password;

        static void setConnection(String mySQLHost, Integer mySQLPort, String mySQLUser,
                                  String mySQLPassword) {
            try {
                if (conn == null || conn.isClosed()) {
                    Class.forName("com.mysql.jdbc.Driver");

                    conn = DriverManager.getConnection("jdbc:mysql://" + mySQLHost + ":" + mySQLPort
                            + "/", mySQLUser, mySQLPassword);
                    System.out.println("connected to mysql:" + mySQLHost + " : " + mySQLPort);
                    host = mySQLHost;
                    port = mySQLPort;
                    user = mySQLUser;
                    password = mySQLPassword;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        static Connection getConnection() {
            try {
                if (conn == null || conn.isClosed()) {
                    setConnection(host, port, user, password);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            return conn;
        }

        static Map<String, List<String>> getColumns(){
            Map<String, List<String>> cols = new HashMap<>();
            Connection conn = getConnection();
            try {
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet r = metaData.getCatalogs();
                String[] tableType = { "TABLE" };
                while (r.next()) {
                    String databaseName = r.getString("TABLE_CAT");
                    ResultSet result = metaData.getTables(databaseName, null, null, tableType);
                    while (result.next()) {
                        String tableName = result.getString("TABLE_NAME");
                        String key = databaseName + "." + tableName;
                        ResultSet colSet = metaData.getColumns(databaseName, null, tableName, null);
                        cols.put(key, new ArrayList<String>());
                        while (colSet.next()) {
                            String column = colSet.getString("COLUMN_NAME");
                            cols.get(key).add(column);
                        }
                    }
                }

            } catch (SQLException e) {
                System.out.println(e);
                return null;
            }
            return cols;
        }
    }

    public static class Test {
        void test() {
            System.out.println("------");
            String name=null;
            name.toString();
        }
    }
    public static class Test1 extends Test {
        @Override
        void test() {
            try {
                super.test();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            new Test1().test();
        }
    }
}
