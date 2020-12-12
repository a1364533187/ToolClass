package design.pattern.decorator;

import org.junit.Test;

public class TestDecorator {

    @Test
    public void test1() {
        Wangmeili wangmeili = new Wangmeili();
        Girl decorateGirl = new SexGirl(new SmartGirl(wangmeili));
        decorateGirl.dance();
    }
}
