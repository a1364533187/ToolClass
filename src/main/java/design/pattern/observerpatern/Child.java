package design.pattern.observerpatern;

import java.util.ArrayList;
import java.util.List;

public class Child {

    private List<WakeUpListener> wakeUpListeners = new ArrayList<>();

    public void addWakeupListener(WakeUpListener feeder) {
        wakeUpListeners.add(feeder);
    }

    public void removeWakeupListener(WakeUpListener feeder) {
        this.wakeUpListeners.remove(feeder);
    }

    public void wakeup(WakeupEvent wakeupEvent) {
        System.out.println("baby am wake up");
        //通知观察者
        for (WakeUpListener listener : wakeUpListeners) {
            listener.actionWakeup(wakeupEvent);
        }
    }
}
