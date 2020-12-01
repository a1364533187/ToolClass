package com.bigcow.com;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

public class CopyProperties {

    @Test
    public void test1() {
        B b = new B();
        b.setName(3);
        A a = new A();
        a.setAge(10);
        b.setA(a);
        C c = new C();
        System.out.println(b);
        //shallow copy
        BeanUtils.copyProperties(b, c);
        System.out.println(c);
        a.setAge(20);
        System.out.println(c);
        System.out.println(b);
    }

    class A {

        private int age;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "A{" +
                    "age=" + age +
                    '}';
        }
    }

    class B {

        private A a;
        private int name;

        public A getA() {
            return a;
        }

        public void setA(A a) {
            this.a = a;
        }

        public int getName() {
            return name;
        }

        public void setName(int name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "B{" +
                    "a=" + a +
                    ", name=" + name +
                    '}';
        }
    }

    class C {

        private A a;
        private int name;

        public A getA() {
            return a;
        }

        public void setA(A a) {
            this.a = a;
        }

        public int getName() {
            return name;
        }

        public void setName(int name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "C{" +
                    "a=" + a +
                    ", name=" + name +
                    '}';
        }
    }
}
