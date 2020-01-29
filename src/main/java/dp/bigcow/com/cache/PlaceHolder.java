package dp.bigcow.com.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * Create by suzhiwu on 2019/01/27
 */
public class PlaceHolder {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .concurrencyLevel(8)
                .recordStats()
                .maximumSize(100) //记录的条数
                .removalListener(new RemovalListener<Object, Object>() {

                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> removalNotification) {
                        System.out.println(removalNotification.getKey() + "----"
                                + removalNotification.getCause());
                    }
                }).expireAfterWrite(1, TimeUnit.SECONDS)
                .refreshAfterWrite(1, TimeUnit.SECONDS) //这个可以解决多个请求需要更新缓存的时候，只有一个可以查询更新，其他block到一定时间，返回旧值
                .build();

        cache.put("1", "1");
        Thread.sleep(1000);
        System.out.println(cache.get("1", new Callable<String>() {

            @Override
            public String call() throws Exception {
                //这里面可以读取数据库， 再返回 //如果从缓存get 不到的话
                return "what";
            }
        }));
    }
}
