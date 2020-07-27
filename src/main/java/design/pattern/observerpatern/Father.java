package design.pattern.observerpatern;

public class Father implements WakeUpListener {

    @Override
    public void actionWakeup(WakeupEvent wakeupEvent) {
        if (!wakeupEvent.isFoodTime()) {
            System.out.println("baba play with baby");
        }
    }
}
