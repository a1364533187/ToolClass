package dp.bigcow.com.zookeeper.lesson1;

import java.util.Arrays;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.junit.Test;

/**
 * Create by suzhiwu on 2019/02/09
 */
public class ZkElectionDemo {

    private static final String PATH = "/demo/leaderlatch";

    /**
     * 利用zookeeper来实现Master选举的基本思路如下：
     *  选择一个根节点（与其他业务隔离），比如/jobMaster，多台机器同时在此节点下面创建一个子节点/jobMaster/lock，
     *  zookeeper保证了最终只有一台机器能够创建成功，那么这台机器将成为Master。由它来执行业务操作。
     *  启动3次test 可以理解为起了3个实例参与选举
     */
    @Test
    public void leaderLatchTest1() {
        //Curator提供了两种选举方案：
        // Leader Latch LeaderLatch则一直持有leadership， 除非调用close方法，否则它不会释放领导权
        // Leader Election 通过LeaderSelectorListener可以对领导权进行控制， 在适当的时候释放领导权，这样每个节点都有可能获得领导权
        //
        testLeaderLatch(1);

    }

    @Test
    public void leaderLatchTest2() {
        //Curator提供了两种选举方案：
        // Leader Latch LeaderLatch则一直持有leadership， 除非调用close方法，否则它不会释放领导权
        // Leader Election
        //
        testLeaderLatch(2);

    }

    @Test
    public void leaderLatchTest3() {
        //Curator提供了两种选举方案：
        // Leader Latch LeaderLatch则一直持有leadership， 除非调用close方法，否则它不会释放领导权
        // Leader Election
        //
        testLeaderLatch(3);

    }

    @Test
    public void testLeaderElection1() {
        testLeaderElection(1);
    }

    @Test
    public void testLeaderElection2() {
        testLeaderElection(2);
    }

    @Test
    public void testLeaderElection3() {
        testLeaderElection(3);
    }

    private void testLeaderElection(int clientId) {
        CuratorFramework client = getClient();
        final String name = "client#" + clientId;
        LeaderSelector leaderSelector = new LeaderSelector(client, PATH,
                new LeaderSelectorListener() {

                    //处理完takeLeadership 会释放掉领导权
                    @Override
                    public void takeLeadership(CuratorFramework client) throws Exception {

                        System.out.println(getServerStatus(true));
                        try {
                            System.out.println(listSentence("I am suzhiwu", true));
                        } catch (NotMasterException e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(2000);
                    }

                    @Override
                    public void stateChanged(CuratorFramework client, ConnectionState newState) {
                        System.out.println(getServerStatus(false));
                        try {
                            System.out.println(listSentence("I am suzhiwu", false));
                        } catch (NotMasterException e) {
                            e.printStackTrace();
                        }
                    }
                });
        try {
            leaderSelector.autoRequeue(); //保证释放掉领导权还可以回收
            leaderSelector.start();
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
            CloseableUtils.closeQuietly(leaderSelector);
        }

    }

    private void testLeaderLatch(int clientId) {
        CuratorFramework client = getClient();
        final LeaderLatch leaderLatch = new LeaderLatch(client, PATH, "client#" + clientId);
        try {
            leaderLatch.addListener(new LeaderLatchListener() {

                @Override
                public void isLeader() {
                    System.out.println(getServerStatus(true));
                    try {
                        System.out.println(listSentence("I am suzhiwu", true));
                    } catch (NotMasterException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void notLeader() {
                    System.out.println(getServerStatus(false));
                    try {
                        System.out.println(listSentence("I am suzhiwu", false));
                    } catch (NotMasterException e) {
                        e.printStackTrace();
                    }
                }
            });
            leaderLatch.start();
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭client 关闭leaderlatch
            CloseableUtils.closeQuietly(client);

            CloseableUtils.closeQuietly(leaderLatch);
        }
    }

    private ZkStatus getServerStatus(boolean isLeader) {
        if (isLeader) {
            return ZkStatus.MASTER;
        } else {
            return ZkStatus.SLAVE;
        }
    }

    public List<String> listSentence(String str, boolean isLeader) throws NotMasterException {
        if (isLeader) {
            String[] splits = str.split(" ");
            return Arrays.asList(splits);
        } else {
            throw new NotMasterException("not master");
        }
    }

    private static CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .retryPolicy(retryPolicy).sessionTimeoutMs(6000).connectionTimeoutMs(3000)
                .namespace("demo").build();
        client.start();
        return client;
    }
}
