package leetcode.skiplist;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class SkiplistTest {

    @Test
    public void testSkip1() {
        Random random = new Random();
        System.out.println(2 << 0);
        System.out.println(2 << 1);
        System.out.println(2 << 2);
        System.out.println(2 << 3);
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(8));
        }
    }

    @Test
    public void testSkip2() {
        Skiplist skiplist = new Skiplist();
        skiplist.add(3);
        skiplist.add(5);
        skiplist.add(1);
        skiplist.add(4);
        skiplist.add(30);
        skiplist.add(11);
        skiplist.add(19);
        skiplist.add(57);
        skiplist.add(23);
        skiplist.add(27);
        skiplist.add(28);
        skiplist.add(37);
        for (int i = 41; i < 100; i++) {
            skiplist.add(i);
        }
        skiplist.printSkiplist();
        Random random = new Random();
        skiplist.addLevel(random, 1);
        Assert.assertEquals(true, skiplist.search(11));
        Assert.assertEquals(false, skiplist.search(2));
        Assert.assertEquals(true, skiplist.search(85));
        Assert.assertEquals(true, skiplist.search(45));
        skiplist.erase(19);
        System.out.println("-----------------");
        skiplist.erase(57);
        skiplist.printSkiplist();
    }

    /**
     * ["Skiplist","add","add","add","add","search","erase","search","search","search"]
     *             [[],[0],[5],[2],[1],[0],[5],[2],[3],[2]]
     */
    @Test
    public void testSkip3() {
        Skiplist skiplist = new Skiplist();
        skiplist.add(0);
        skiplist.add(5);
        skiplist.add(2);
        skiplist.add(1);
        skiplist.printSkiplist();
        Assert.assertEquals(true, skiplist.search(0));
        Assert.assertEquals(true, skiplist.erase(5));
        Assert.assertEquals(true, skiplist.search(2));
        Assert.assertEquals(false, skiplist.search(3));
        Assert.assertEquals(true, skiplist.search(2));
        System.out.println("---------------");
        skiplist.printSkiplist();
    }

    /**
     * ["Skiplist","add","add","add","add","add","erase","erase","add","search","search","add","erase","search","add","add","add","erase","search","erase","search","search","search","erase","erase","search","erase","add","add","erase","add","search","search","search","search","search"]
     * [[],[9],[4],[5],[6],[9],[2],[1],[2],[7],[4],[5],[6],[5],[6],[7],[4],[3],[6],[3],[4],[3],[8],[7],[6],[7],[4],[1],[6],[3],[4],[7],[6],[1],[0],[3]]
     */
    @Test
    public void testSkipList3() {
        Skiplist skiplist = new Skiplist();
        skiplist.add(9);
        skiplist.add(4);
        skiplist.add(5);
        skiplist.add(6);
        skiplist.add(9);
        Assert.assertEquals(false, skiplist.erase(2));
        Assert.assertEquals(false, skiplist.erase(1));
        skiplist.add(2);
        skiplist.printSkiplist();
        Assert.assertEquals(false, skiplist.search(7));
        Assert.assertEquals(true, skiplist.search(4));
        System.out.println("----------------");
        skiplist.printSkiplist();
        skiplist.add(5);
        skiplist.erase(6);
        skiplist.search(5);
    }

}
