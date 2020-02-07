package dp.bigcow.com.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import dp.bigcow.com.zookeeper.lesson1.ZkStatus;

/**
 * create by suzhiwu on 2019/02/09
 */
public class ZkClientFactory {

    private final static String ZK_ADDRESS = "127.0.0.1:2181";

    public static CuratorFramework createSinmple(String connectStr) {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        return CuratorFrameworkFactory.newClient(connectStr, retryPolicy);
    }

    public static CuratorFramework createWithOptions(String connectStr, RetryPolicy retryPolicy,
            int connectTimeoutMs, int sessionTimeoutMs) {
        return CuratorFrameworkFactory.builder().connectString(connectStr).retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectTimeoutMs).sessionTimeoutMs(sessionTimeoutMs).build();
    }

    public static void createZnode(String zkPath, CreateMode createMode, byte[] payload) throws Exception {
        CuratorFramework client = createSinmple(ZK_ADDRESS);
        client.start();
        client.create().creatingParentsIfNeeded().withMode(createMode).forPath(zkPath, payload);
    }

    @Test
    public void createZnode() throws Exception {
        createZnode("/test/node-1", CreateMode.PERSISTENT, "hello".getBytes());
    }
}
