package com.bigcow.spring.highconcurrency;

import java.util.List;

/**
 * Create by suzhiwu on 2020/06/27
 */
public interface Processor<T> {

    void process(List<T> list);
}
