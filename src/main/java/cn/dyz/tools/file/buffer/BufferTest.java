package cn.dyz.tools.file.buffer;

import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * Create by suzhiwu on 2019/12/11
 */
public class BufferTest {

    @Test
    public void test1() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put("ABC".getBytes());
        System.out.println(byteBuffer.remaining());
        byteBuffer.flip();
        byteBuffer.get();
        System.out.println(byteBuffer.remaining());
    }

    @Test
    public void test2() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
        byteBuffer.put("ABC".getBytes());
        System.out.println(byteBuffer.remaining());
        byteBuffer.flip();
        byteBuffer.get();
        System.out.println(byteBuffer.remaining());
    }
}
