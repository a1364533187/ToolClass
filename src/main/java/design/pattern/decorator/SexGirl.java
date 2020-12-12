package design.pattern.decorator;

public class SexGirl extends AbstractDecorator {

    public SexGirl(Girl girl) {
        super(girl);
    }

    @Override
    public void dance() {
        //添加装饰
        System.out.println("--sex--");
        girl.dance();
    }
}
