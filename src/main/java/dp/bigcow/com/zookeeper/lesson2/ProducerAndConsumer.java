package dp.bigcow.com.zookeeper.lesson2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.SimpleDistributedQueue;

import dp.bigcow.com.zookeeper.ZkClientFactory;

/**
 * Create by suzhiwu on 2019/02/09
 */
public class ProducerAndConsumer {

    CuratorFramework client = ZkClientFactory.createSinmple("127.0.0.1:2181");
    SimpleDistributedQueue distributedQueue = new SimpleDistributedQueue(client, "/zhiwu/lock");

    public static void main(String[] args) {

    }
}
