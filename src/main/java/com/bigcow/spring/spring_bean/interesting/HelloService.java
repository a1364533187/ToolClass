package com.bigcow.spring.spring_bean.interesting;

import java.util.List;

import com.bigcow.spring.Su;

public interface HelloService {

    @Su("select xxx from xxx.api1")
    List<String> sayName(int key);

    @Su("select xxx from xxx.api2")
    String sayHi(String url);
}
