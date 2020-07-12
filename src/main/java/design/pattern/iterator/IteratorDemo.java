package design.pattern.iterator;

import java.util.Iterator;

public class IteratorDemo {

    public static void main(String[] args) {
        ReverseArrayCollection<String> reverseArrayCollection = new ReverseArrayCollection<>("1", "2", "3");
        Iterator<String> iterators = reverseArrayCollection.iterator();
        while (iterators.hasNext()) {
            String iter = iterators.next();
            System.out.println("--->" + iter);
        }
    }
}
