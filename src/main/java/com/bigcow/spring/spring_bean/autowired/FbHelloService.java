package com.bigcow.spring.spring_bean.autowired;

import org.springframework.stereotype.Service;

@Service
public class FbHelloService implements HelloService {

    @Override
    public void sayHello() {
        System.out.println("facebook haha");
    }
}
