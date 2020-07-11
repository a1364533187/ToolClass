package design.pattern;

/**
 * Create by suzhiwu on 2020/07/10
 */
public class TemplateMethod {

    public static void main(String[] args) {
        Worker1 worker1 = new Worker1();
        worker1.doTemplate(); //模版可以带一些模版内容
    }
}

abstract class AbsTpl {

    public void doTemplate() {
        System.out.println("startxxx---->");
        doSomething();
        System.out.println("endxxx--->");

    }

    protected abstract void doSomething();
}

class Worker1 extends AbsTpl {

    @Override
    protected void doSomething() {
        System.out.println("----> ");
    }
}