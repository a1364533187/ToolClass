package dp.bigcow.com.cache;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Create by suzhiwu on 2019/02/09
 */
public class LoadingCacheDemo {
    //创建一个LoadingCache，并可以进行一些简单的缓存配置
    private static LoadingCache<String, String > loadingCache = CacheBuilder.newBuilder()
            //最大容量为100（基于容量进行回收）
            .maximumSize(100)
            //配置写入后多久使缓存过期-下文会讲述
            .expireAfterWrite(150, TimeUnit.SECONDS)
            //配置写入后多久刷新缓存-下文会讲述
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            //key使用弱引用-WeakReference
            .weakKeys()
            //当Entry被移除时的监听器
            .removalListener(notification -> System.out.println(notification.getCause() + "--->key: " + notification.getKey()))
            //创建一个CacheLoader，重写load方法，以实现"当get时缓存不存在，则load，放到缓存，并返回"的效果
            .build(new CacheLoader<String, String>() {
                //重点，自动写缓存数据的方法，必须要实现
                @Override
                public String load(String key) throws Exception {
                    return "value_" + key;
                }
                //异步刷新缓存-下文会讲述
                @Override
                public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                    return super.reload(key, oldValue);
                }
            });

    @Test
    public void getTest() throws Exception {
        //测试例子，调用其get方法，cache会自动加载并返回
        String value = loadingCache.get("2");
        //返回value_1
        System.out.println("value: " + value);
    }
}
