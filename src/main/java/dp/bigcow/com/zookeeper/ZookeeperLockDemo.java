package dp.bigcow.com.zookeeper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

public class ZookeeperLockDemo {
    public static void main(String[] args) throws InterruptedException {
        CuratorFramework client = ZkClientFactory.createSinmple("127.0.0.1:2183");
        client.start(); //需要启动client 才能使用zk 分布式锁
        InterProcessMutex lock = new InterProcessMutex(client, "/lock");
        ExecutorService service = new ThreadPoolExecutor(10,10, 1, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        CountDownLatch latch = new CountDownLatch(10000);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            final int temp = i;
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try {
//                        lock.acquire();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    list.add(temp);
                    try {
//                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                }
            });
        }
        latch.await();
        System.out.println("--->" + list.size());
        service.shutdown();
    }
}
