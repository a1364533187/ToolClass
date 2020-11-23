package design.pattern.observerpatern.javaObeserver;

import java.util.EventListener;

public interface DoorListener extends EventListener {

    public void doorEvent(DoorEvent event);
}
