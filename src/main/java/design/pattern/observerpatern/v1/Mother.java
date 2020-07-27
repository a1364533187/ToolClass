package design.pattern.observerpatern.v1;

public class Mother implements Runnable {

    private Child child;

    public Mother(Child child) {
        this.child = child;
    }

    @Override
    public void run() {
        while (!child.isWakeup()) {}
        System.out.println("该给宝宝喂食了");
    }
}
