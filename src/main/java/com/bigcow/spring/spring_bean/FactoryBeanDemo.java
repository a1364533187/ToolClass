package com.bigcow.spring.spring_bean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试spring factory bean
 */
public class FactoryBeanDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("容器启动完成");
        UserService userService = applicationContext.getBean(UserService.class);
        System.out.println(userService);
        Object customerFactoryBean = applicationContext.getBean("customFactoryBean");
        System.out.println(customerFactoryBean);
    }
}
