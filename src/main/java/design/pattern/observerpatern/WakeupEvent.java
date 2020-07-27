package design.pattern.observerpatern;

/**
 * Create by suzhiwu on 2020/07/26
 */
public class WakeupEvent {

    private boolean isFoodTime; //是吃饭的时间
    private Child source;

    public WakeupEvent(boolean isFoodTime, Child source) {
        this.isFoodTime = isFoodTime;
        this.source = source;
    }

    public boolean isFoodTime() {
        return isFoodTime;
    }

    public void setFoodTime(boolean foodTime) {
        isFoodTime = foodTime;
    }

    public Child getSource() {
        return source;
    }

    public void setSource(Child source) {
        this.source = source;
    }
}
