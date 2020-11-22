package com.bigcow.proxy;

public class HelloServiceImpl implements HelloService {

    private String msg;

    @Override
    public void say(String msg) {
        this.msg = msg;
        System.out.println("say: " + msg);
    }

    @Override
    public String echo(String msg) {
        System.out.println("echo" + msg);
        return msg;
    }

    @Override
    public String[] getHobbies() {
        return new String[] { msg };
    }
}
