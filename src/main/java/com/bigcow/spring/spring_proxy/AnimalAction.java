package com.bigcow.spring.spring_proxy;

/**
 * Create by suzhiwu on 2020/06/21
 */
public class AnimalAction implements PeopleAction {

    @Override
    public void eat() {
        System.out.println("我是动物");
    }
}
