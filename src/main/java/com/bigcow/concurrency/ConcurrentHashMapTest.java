package com.bigcow.concurrency;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

public class ConcurrentHashMapTest {

    private ConcurrentHashMap<List<String>, String> map = new ConcurrentHashMap<>();

    @Test
    public void test1() {
        final List<String> list1 = Lists.newArrayList("1", "2");
        map.put(list1, "1");
        final List<String> list2 = Lists.newArrayList("2", "1");
        map.put(list2, "2");

        Assert.assertEquals("1", map.get(list1));
        Assert.assertEquals("2", map.get(list2));

        final List<String> list3 = Lists.newArrayList("1", "2");
        map.put(list3, "1");
        final List<String> list4 = Lists.newArrayList("2", "2");
        map.put(list4, "2");

        Assert.assertEquals("2", map.get(list1));
        Assert.assertEquals("2", map.get(list2));
    }
}
