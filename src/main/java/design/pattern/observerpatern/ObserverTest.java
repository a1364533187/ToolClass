package design.pattern.observerpatern;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

public class ObserverTest {

    public static void main(String[] args) {
        Child child = new Child();
        child.addWakeupListener(new Mother());
        child.addWakeupListener(new Father());

        child.wakeup(new WakeupEvent(true, child));
    }

    @Test
    public void genDict() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("dict.txt"));
        for (int i1 = 0; i1 <= 9; i1++) {
            for (int i2 = 0; i2 <= 9; i2++) {
                for (int i3 = 0; i3 <= 9; i3++) {
                    for (int i4 = 0; i4 < 9; i4++) {
                        for (int i5 = 0; i5 < 9; i5++) {
                            for (int i6 = 0; i6 < 9; i6++) {
                                for (int i7 = 0; i7 < 9; i7++) {
                                    for (int i8 = 0; i8 < 9; i8++) {
                                        out.write("" + i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8);
                                        out.newLine();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        out.close();
    }
}
