package com.bigcow.com.rangemap;

import org.junit.Test;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Range;

/**
 * Create by suzhiwu on 2020/03/03
 */
public class RangeMapDemo {

    @Test
    public void TestRangeMap() {
        ImmutableRangeMap<Integer, Integer> v6Map = ImmutableRangeMap.of();
        //ImmutableSortedMap<Integer, Integer> v4Map = ImmutableSortedMap.of();
        ImmutableRangeMap.Builder<Integer, Integer> builder = ImmutableRangeMap.builder();
        builder.put(Range.closed(1, 4), 10);

        builder.put(Range.closed(3, 10), 10);

        v6Map = builder.build();
        System.out.println(v6Map.get(7));

    }

    @Test
    public void TestSortedMap() {
        ImmutableSortedMap.Builder<Integer, Integer> v4Map = ImmutableSortedMap.naturalOrder();
        v4Map.put(1, 2);
        v4Map.put(2, 3);
        v4Map.put(10, 3);
        v4Map.put(8, 5);
        System.out.println(v4Map.build().tailMap(5).get(v4Map.build().tailMap(5).firstKey()));
    }
}
