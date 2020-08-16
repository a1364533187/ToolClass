package design.pattern.observerpatern;

public class ObserverTest {

    public static void main(String[] args) {
        Child child = new Child();
        child.addWakeupListener(new Mother());
        child.addWakeupListener(new Father());

        child.wakeup(new WakeupEvent(true, child));
    }
}
