package com.bigcow.dubbo.spi;

import java.util.List;

/**
 * Create by suzhiwu on 2020/03/06
 */
public interface Search {
    public List<String> searchDoc(String keyword);
}
