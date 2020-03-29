package com.bigcow.learner.demo1;

public class GreetingsServiceImpl implements GreetingService {

    @Override
    public String sayHi(String name) {
        return "hi, " + name;
    }
}
