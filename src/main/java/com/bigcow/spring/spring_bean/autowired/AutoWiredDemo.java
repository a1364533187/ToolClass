package com.bigcow.spring.spring_bean.autowired;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.bigcow.spring.spring_bean.AppConfig;

@ComponentScan("com.bigcow.spring.spring_bean.autowired")
public class AutoWiredDemo {

    @Test
    public void testAutowired() throws InterruptedException {
        //先装配上下文看看
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                AppConfig.class);
        System.out.println("容器启动完成");
        AutowiredTest autowiredTest = applicationContext.getBean(AutowiredTest.class);
        autowiredTest.testFbHello();
        autowiredTest.testGoogleHello();

        new CountDownLatch(1).await();
    }
}
