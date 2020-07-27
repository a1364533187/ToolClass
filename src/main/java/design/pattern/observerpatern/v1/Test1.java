package design.pattern.observerpatern.v1;

public class Test1 {

    public static void main(String[] args) throws InterruptedException {
        Child child = new Child();
        Mother mother = new Mother(child);

        new Thread(child).start();
        new Thread(mother).start();
    }
}
