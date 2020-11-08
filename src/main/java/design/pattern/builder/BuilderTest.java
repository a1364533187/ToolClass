package design.pattern.builder;

import org.junit.Test;

public class BuilderTest {

    @Test
    public void test1() {
        Human human = Human.newBuilder()
                .body("body1")
                .hand("hand1")
                .leg("leg1")
                .build();
        System.out.println("--->" + human);
    }

    @Test
    public void test3() {
        // 非 Builder 模式
        Computer computer = new Computer("cpu", "screen", "memory", "mainboard");
        // Builder 模式
        NewComputer newComputer = NewComputer.newBuilder().cpu("cpu").screen("screen")
                .memory("memory").mainboard("mainboard").build();
    }

}
