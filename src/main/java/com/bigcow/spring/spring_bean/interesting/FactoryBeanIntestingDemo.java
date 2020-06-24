package com.bigcow.spring.spring_bean.interesting;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 测试spring factory bean
 */
@ComponentScan("com.bigcow.spring.spring_bean.interesting")
@Import(MyBeanDefinitionRegistrar.class)
public class FactoryBeanIntestingDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                FactoryBeanIntestingDemo.class);
        System.out.println("容器启动完成");
        HelloService helloService = (HelloService) applicationContext.getBean("com.bigcow.spring.spring_bean.interesting.HelloService");
        System.out.println("--->" + helloService.sayHi("suzhiwu"));

        HiService hiService = (HiService) applicationContext.getBean("com.bigcow.spring.spring_bean.interesting.HiService");
        System.out.println("--->" + hiService.sayWhat("yangjie"));
//        System.out.println("--->" + helloService.sayName(7));
    }
}
