package com.bigcow.spring.spring_aop;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.bigcow.spring.spring_aop")
public class AopTest {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AopTest.class,
                args);
        Calcate calcate = applicationContext.getBean(Calcate.class);
        int value = calcate.add(3, 4);
        System.out.println("--->" + value);
    }
}
