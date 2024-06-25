package com.unicam.zookeeper.synchronization;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SyncPrimitive implements Watcher, CommandLineRunner {
    @Value("${zookeeper.server.uri}")
    private String zookeeperServerUri;

    static ZooKeeper zk = null;
    static Integer mutex;
    String root;

    SyncPrimitive(@Value("${zookeeper.server.uri}") String zookeeperServerUri) {
        if (zk == null) {
            try {
                System.out.println("Starting ZK:");
                zk = new ZooKeeper(zookeeperServerUri, 3000, this);
                mutex = new Integer(-1);
                System.out.println("Finished starting ZK: " + zk);
            } catch (IOException e) {
                System.out.println(e.toString());
                zk = null;
            }
        }
        // else mutex = new Integer(-1);
    }

    synchronized public void process(WatchedEvent event) {
        synchronized (mutex) {
            // System.out.println("Process: " + event.getType());
            mutex.notify();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            System.out.println("Process type:" + args[0]);
            System.out.println("Count:" + args[1]);
            if (args[0].equals("barrier"))
                barrierTest(args);
            else
                queueTest(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("Invalid arguments");
            System.err.println(
                    "Please use the following pattern to successfully run the proccess you need to excecute");
            System.err.println(
                    "mvn spring-boot:run -Dspring-boot.run.arguments=\"[producer|consumer|barrier] [number]\"");

        }

    }

    public void queueTest(String args[]) {
        Queue q = new Queue(zookeeperServerUri, "/app1");

        System.out.println("Input: " + zookeeperServerUri);
        int i;
        Integer max = new Integer(args[1]);

        if (args[0].equals("producer")) {
            System.out.println("Producer");
            for (i = 0; i < max; i++)
                try {
                    q.produce(10 + i);
                } catch (KeeperException e) {

                } catch (InterruptedException e) {

                }
        } else if (args[0].equals("consumer")) {
            System.out.println("Consumer");

            for (i = 0; i < max; i++) {
                try {
                    int r = q.consume();
                    System.out.println("Item: " + r);
                } catch (KeeperException e) {
                    i--;
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void barrierTest(String args[]) {
        Barrier b = new Barrier(zookeeperServerUri, "/b1", new Integer(args[1]));
        try {
            boolean flag = b.enter();
            System.out.println("Entered barrier: " + args[1]);
            if (!flag)
                System.out.println("Error when entering the barrier");
        } catch (KeeperException e) {
        } catch (InterruptedException e) {
        }

        // Generate random integer
        Random rand = new Random();
        int r = rand.nextInt(100);
        // Loop for rand iterations
        for (int i = 0; i < r; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        try {
            b.leave();
        } catch (KeeperException e) {

        } catch (InterruptedException e) {

        }
        System.out.println("Left barrier");
    }
}
