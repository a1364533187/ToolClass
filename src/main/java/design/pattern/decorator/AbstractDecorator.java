package design.pattern.decorator;

public class AbstractDecorator implements Girl {

    protected Girl girl;

    public AbstractDecorator(Girl girl) {
        this.girl = girl;
    }

    @Override
    public void dance() {
        girl.dance();
    }
}
