package com.bigcow.spring.spring_aop;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

@Service
@EnableAspectJAutoProxy
public class Calcate {

    public int add(int a, int b) {
        System.out.println(a + "--->" + b);
        return a + b;
    }
}
