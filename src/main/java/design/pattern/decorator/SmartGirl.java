package design.pattern.decorator;

public class SmartGirl extends AbstractDecorator {

    public SmartGirl(Girl girl) {
        super(girl);
    }

    @Override
    public void dance() {
        System.out.println("--smart--");
        girl.dance();
    }
}
