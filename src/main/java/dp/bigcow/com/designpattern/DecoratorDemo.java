package dp.bigcow.com.designpattern;

/**
 * Create by suzhiwu on 2020/03/02
 */
public class DecoratorDemo {

    /**
     * In plain words
     *
     * Decorator pattern lets you dynamically change the behavior of an object at run time by wrapping them in an object of a decorator class.
     * 类似与游戏里的技能加强
     * @param args
     */
    public static void main(String[] args) {
        //simple troll
        Troll troll = new SimpleTroll();
        System.out.println("--->" + troll.getAttackPower());
        troll.attack();

        //Enhance troll
        Troll enhanceTroll = new EnhanceTroll(troll);
        System.out.println("--->" + enhanceTroll.getAttackPower());
        enhanceTroll.attack();

    }

}

interface Troll { //侏儒

    void attack();

    int getAttackPower();

    void fleeBattle();
}

class SimpleTroll implements Troll {

    public SimpleTroll() {
    }

    @Override
    public void attack() {
        System.out.println("glab you");
    }

    @Override
    public int getAttackPower() {
        return 10;
    }

    @Override
    public void fleeBattle() {
        System.out.println("horror");
    }
}

class EnhanceTroll implements Troll {

    private Troll troll;

    public EnhanceTroll(Troll troll) {
        this.troll = troll;
    }

    @Override
    public void attack() {
        troll.attack();
        System.out.println("kill you");
    }

    @Override
    public int getAttackPower() {
        //能力值加成
        return troll.getAttackPower() + 10;
    }

    @Override
    public void fleeBattle() {
        troll.fleeBattle();
        System.out.println("just so so ");
    }
}
