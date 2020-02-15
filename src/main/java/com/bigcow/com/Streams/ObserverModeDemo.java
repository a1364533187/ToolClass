package com.bigcow.com.Streams;

import java.util.Observable;
import java.util.Observer;

import org.junit.Test;

/**
 * Create by suzhiwu on 2019/02/19
 */
public class ObserverModeDemo {

    /**
     * 2.1 角色：被观察对象Observable (报社， 老师)，观察者Observer（订阅报纸的人， 学生）
     *
     * 2.2 关系：
     * 1）.被观察对象：观察者 = 1：n
     * 2）.被观察对象状态发生变化，会通知所有观察者，观察者将做出相应的反应
     */
    @Test
    public void JavaUtil() {
        Teacher t1 = new Teacher();
        Student s1 = new Student(1);
        t1.addObserver(s1);
        t1.setAction("上课");
        t1.setAction("上课");

        Student s2 = new Student(2);
        t1.addObserver(s2);
        t1.addObserver(s2); //添加观察者对象会去重
        t1.setAction("下课");
        t1.setAction("放学");

    }
}

class Student implements Observer {

    private int stuNo;

    Student(int i) {
        this.stuNo = i;
    }

    public int getStuNo() {
        return stuNo;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("同学 " + stuNo + "收到了：" + arg);
    }
}

class Teacher extends Observable {

    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        if (action != this.action) {
            setChanged();
            notifyObservers(action);
        }
        this.action = action;
    }

}
