package cn.dyz.tools.file.function;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Test;

/**
 * Create by suzhiwu on 2019/02/09
 */
public class FunctionDemo {

    //有输入，无输出
    @Test
    public void testComsumer() {
        Consumer f = System.out::println;
        Consumer f2 = n -> System.out.println(n + "-F2");

        //执行完F后再执行F2的Accept方法
        f.andThen(f2).accept("test");

        //连续执行F的Accept方法
        f.andThen(f).andThen(f).andThen(f).accept("test1");
    }

    @Test
    public void testFunction() {
        Function<Integer, Integer> f = s -> s++;
        Function<Integer, Integer> g = s -> s * 2;

        /**
         * 下面表示在执行F时，先执行G，并且执行F时使用G的输出当作输入。
         * 相当于以下代码：
         * Integer a = g.apply(1);
         * System.out.println(f.apply(a));
         */
        System.out.println(f.compose(g).apply(1));

        /**
         * 表示执行F的Apply后使用其返回的值当作输入再执行G的Apply；
         * 相当于以下代码
         * Integer a = f.apply(1);
         * System.out.println(g.apply(a));
         */
        System.out.println(f.andThen(g).apply(1));

        /**
         * identity方法会返回一个不进行任何处理的Function，即输出与输入值相等；
         */
        System.out.println(Function.identity().apply("a"));
    }

    //处理推断
    @Test
    public void testPredicate() {
        Predicate<String> p = o -> o.equals("test");
        Predicate<String> g = o -> o.startsWith("t");

        /**
         * negate: 用于对原来的Predicate做取反处理；
         * 如当调用p.test("test")为True时，调用p.negate().test("test")就会是False；
         */
        Assert.assertFalse(p.negate().test("test"));

        /**
         * and: 针对同一输入值，多个Predicate均返回True时返回True，否则返回False；
         */
        Assert.assertTrue(p.and(g).test("test"));

        /**
         * or: 针对同一输入值，多个Predicate只要有一个返回True则返回True，否则返回False
         */
        Assert.assertTrue(p.or(g).test("ta"));
    }

    @Test
    public void testStream() {
        List<String> list = Arrays.asList("a", "b", "c", "d", "ab");
        list.stream().filter(o -> o.contains("a")).forEachOrdered(o -> System.out.println(o));
        System.out.println(list.stream().filter(o -> o.contains("a")).anyMatch(o -> o.contains("a")));
    }

    @Test
    public void testOptional() {
        //        Optional o = Optional.empty();
        //        //null 会抛异常
        //        System.out.println(o.get());

        //        Optional<String> s1 = Optional.of("haha");
        System.out.println(Optional.ofNullable(null));
        Optional<String> s2 = Optional.empty();
        System.out.println(s2.orElse("susu"));
    }

    //无输入， 有输出, 可以用于惰性求值， 需要的时候使用
    @Test
    public void testSupplier() {
        Supplier<Map> supplier = () -> new HashMap<String, String>();
        Map<String, String> map = supplier.get();
        map.put("1", "1");
        System.out.println(map);

        System.out.println(supplier.get());
    }

}
