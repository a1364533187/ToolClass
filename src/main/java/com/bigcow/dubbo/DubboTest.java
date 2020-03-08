package com.bigcow.dubbo;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.bigcow.dubbo.spi.Search;

/**
 * Create by suzhiwu on 2020/03/06
 */
public class DubboTest {

    /**
     * https://zhuanlan.zhihu.com/p/28909673
     * @param args
     */
    public static void main(String[] args) {
        ServiceLoader<Search> s = ServiceLoader.load(Search.class);
        Iterator<Search> iterator = s.iterator();
        while (iterator.hasNext()) {
            Search search =  iterator.next();
            search.searchDoc("hello world");
        }
    }
}
