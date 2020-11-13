package cn.dyz.tools.file;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Create by suzhiwu on 2020/11/13
 */
public class generic {

    public static <T extends Comparable<T>> void sort1(List<T> list) {
        Collections.sort(list);
    }

    public static <T extends Comparable<? super T>> void sort2(List<T> list) {
        Collections.sort(list);
    }

    public static void main(String[] args) {
        List<Father> fathers = new ArrayList<Father>();
        fathers.add(new Father(30));
        fathers.add(new Father(20));

        List<Son> sons = new ArrayList<Son>();
        sons.add(new Son(10));
        sons.add(new Son(5));

        //        sort1(fathers);
        //        sort1(sons); //会报错

        sort2(fathers);
        sort2(sons);

        System.out.println("-->" + fathers);
        System.out.println("-->" + sons);
    }
}

class Father implements Comparable<Father> {

    private Integer age;

    public Father(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public int compareTo(Father o) {
        return this.age - o.age;
    }

    @Override
    public String toString() {
        return "Father{" + "age=" + age + '}';
    }
}

class Son extends Father {

    public Son(int age) {
        super(age);
    }
}