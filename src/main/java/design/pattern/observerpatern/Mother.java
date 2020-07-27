package design.pattern.observerpatern;

public class Mother implements WakeUpListener {

    @Override
    public void actionWakeup(WakeupEvent wakeupEvent) {
        if (wakeupEvent.isFoodTime()) {
            System.out.println("mama feed child milk");
        }
    }
}
