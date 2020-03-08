package com.bigcow.dubbo.spi;

import java.util.List;

/**
 * Create by suzhiwu on 2019/03/05
 */
public class DatabaseSearch implements Search {

    @Override
    public List<String> searchDoc(String keyword) {
        System.out.println("数据搜索 " + keyword);
        return null;
    }
}
