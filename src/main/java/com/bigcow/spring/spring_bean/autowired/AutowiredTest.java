package com.bigcow.spring.spring_bean.autowired;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class AutowiredTest {

    @PostConstruct
    public void testPostConstruct() {
        System.out.println("---->");
    }

    @Autowired
    @Qualifier("fbHelloService")
    private HelloService helloService;

    @Autowired
    @Qualifier("googleHelloService")
    private HelloService googleHelloService;

    public void testFbHello() {
        helloService.sayHello();
    }

    public void testGoogleHello() {
        googleHelloService.sayHello();
    }

    @Scheduled(cron = "0/1 * * * * *")
    public void schedule1() {
        System.out.println("i am scheduled");
    }
}
