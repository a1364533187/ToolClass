package leetcode;

import org.junit.Assert;
import org.junit.Test;

public class LRUCacheTest {

    @Test
    public void test1() {
        LRUCache lruCache = new LRUCache(2);
        lruCache.put(2, 1);
        lruCache.put(1, 1);
        lruCache.put(2, 3);
        lruCache.put(4, 1);
        Assert.assertEquals(-1, lruCache.get(1));
        Assert.assertEquals(3, lruCache.get(2));
    }

    @Test
    public void test2() {
        LRUCache lruCache = new LRUCache(10);
        lruCache.put(10, 13);
        lruCache.put(3, 17);
        lruCache.put(6, 11);
        lruCache.put(10, 5);
        lruCache.put(9, 10);
        Assert.assertEquals(-1, lruCache.get(13));
        lruCache.put(2, 19);
        Assert.assertEquals(19, lruCache.get(2));
        Assert.assertEquals(17, lruCache.get(3));
        lruCache.put(5, 25);
        Assert.assertEquals(-1, lruCache.get(8));
        lruCache.put(9, 22);
        lruCache.put(5, 5);
        lruCache.put(1,30);
        Assert.assertEquals(-1, lruCache.get(11));
        lruCache.put(9,12);
        Assert.assertEquals(-1, lruCache.get(7));
        Assert.assertEquals(5, lruCache.get(5));
        Assert.assertEquals(-1, lruCache.get(8));
        Assert.assertEquals(12, lruCache.get(9));
        lruCache.put(4,30);
        lruCache.put(9,3);
    }
}
