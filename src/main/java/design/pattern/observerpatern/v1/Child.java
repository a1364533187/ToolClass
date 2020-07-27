package design.pattern.observerpatern.v1;

public class Child implements Runnable {

    private volatile boolean isWakeup = false;

    public boolean isWakeup() {
        return isWakeup;
    }

    public void setWakeup(boolean wakeup) {
        isWakeup = wakeup;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                System.out.println("宝宝接着睡一会");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("baby is wakeup");
        this.isWakeup = true;
    }
}
