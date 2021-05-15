package com.test.zookeeper;


import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * zookeeper测试类
 *
 * @author lz-119612
 * @version 1.0
 * @date 2019/11/27 14:10
 **/
public class ZookeeperTest implements Runnable {

    private static String membershipRoot = "/Members";
    private final Watcher connectionWatcher;
    private final Watcher childrenWatcher;
    private ZooKeeper zk;
    boolean alive = true;
    /*<dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.8</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-api</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-recipes</artifactId>
            <version>2.11.1</version>
        </dependency>*/

    public ZookeeperTest(String HostPort) throws IOException, InterruptedException, KeeperException {
        connectionWatcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == Event.EventType.None && event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.printf("\nEvent Received: %s", event.toString());
                }
            }
        };

        childrenWatcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.printf("\nEvent Received: %s", event.toString());
                if (event.getType() == Event.EventType.NodeChildrenChanged) {
                    try {
                        //Get current list of child znode,
                        //reset the watch
                        List<String> children = zk.getChildren(membershipRoot, this);
                        wall("!!!Cluster Membership Change!!!");
                        wall("Members: " + children);
                    } catch (KeeperException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        alive = false;
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        zk = new ZooKeeper(HostPort, 2000, connectionWatcher);

        // Ensure the parent znode exists
        if (zk.exists(membershipRoot, false) == null) {
            zk.create(membershipRoot, "ZookeeperTestRoot".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        // Set a watch on the parent znode
        List<String> children = zk.getChildren(membershipRoot, childrenWatcher);
        System.err.println("Members: " + children);


        String newPath = membershipRoot + "/Child000Z";
        for (int i = 0;i<=5;i++) {
            if (zk.exists(newPath + 1, false) == null) {
                zk.create(newPath + 1, (newPath + 1).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
            }
        }

        List<String> childrenNew = zk.getChildren(membershipRoot, childrenWatcher);
        System.err.println("-------------------Members: " + childrenNew);

    }

    public synchronized void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void wall(String message) {
        System.out.printf("\nMESSAGE: %s", message);
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (alive) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            this.close();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String hostPort = "localhost:2181,localhost:2182,localhost:2183";
        new ZookeeperTest(hostPort);

        Thread.sleep(3600000);
    }
}
