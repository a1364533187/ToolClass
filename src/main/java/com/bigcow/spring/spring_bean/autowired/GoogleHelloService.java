package com.bigcow.spring.spring_bean.autowired;

import org.springframework.stereotype.Service;

@Service
public class GoogleHelloService implements HelloService {

    @Override
    public void sayHello() {
        System.out.println("ggogle hello");
    }
}
