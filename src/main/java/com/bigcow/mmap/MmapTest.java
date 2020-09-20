package com.bigcow.mmap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

/**
 * Create by suzhiwu on 2020/09/20
 */
public class MmapTest {

    @Test
    public void test1() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("dict.txt", "rw");
        FileChannel fc = raf.getChannel();
        System.out.println("--->" + fc.size());
        //将test.txt文件所有数据映射到虚拟内存，并只读
        MappedByteBuffer mbuff = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        byte[] bytes = new byte[9];
        for (int i = 1; i < 64; i++) {
            mbuff.get(bytes);
            System.out.println(new String(bytes));
        }
    }

    @Test
    public void testMmapWrong1() throws IOException {
        long start = System.currentTimeMillis();
        RandomAccessFile raf = new RandomAccessFile("dict.txt", "rw");
        FileChannel fc = raf.getChannel();
        //将test.txt文件所有数据映射到虚拟内存，并只读
        MappedByteBuffer mbuff = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        byte[] bytes = new byte[9];
        long total = fc.size(); //fc.size(） 操作很耗时， 这样测试总体耗时1min
        for (long i = 1; i < total / 9; i++) {
            mbuff.get(bytes);
        }
        ummap(mbuff);
        fc.close();
        System.out.println(System.currentTimeMillis() - start); // 843ms、920ms、919ms
    }

    @Test
    public void testMmapCost1() throws IOException {
        mmapCost(9);
        mmapCost(1024);
        mmapCost(4 * 1024);
        mmapCost(16 * 1024);
        mmapCost(4 * 1024 * 1024);
        /**
         * 9--->780
         * 1024--->304
         * 4096--->251
         * 16384--->274
         * 4194304--->353
         *
         * 9--->714
         * 1024--->302
         * 4096--->275
         * 16384--->249
         * 4194304--->294
         */
    }

    private void mmapCost(int byteSize) throws IOException {
        long start = System.currentTimeMillis();
        RandomAccessFile raf = new RandomAccessFile("dict.txt", "rw");
        FileChannel fc = raf.getChannel();
        //将test.txt文件所有数据映射到虚拟内存，并只读
        MappedByteBuffer mbuff = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        byte[] bytes = new byte[byteSize];
        long count = fc.size() / byteSize;
        for (long i = 1; i < count; i++) {
            mbuff.get(bytes);
        }
        ummap(mbuff);
        fc.close();
        System.out.println(byteSize + "--->" + (System.currentTimeMillis() - start)); // 843ms、920ms、919ms
    }

    @Test
    public void testMmapCost2() throws IOException {
        long start = System.currentTimeMillis();
        RandomAccessFile raf = new RandomAccessFile("dict.txt", "rw");
        FileChannel fc = raf.getChannel();
        //将test.txt文件所有数据映射到虚拟内存，并只读
        MappedByteBuffer mbuff = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        int byteSize = 4 * 1024;
        byte[] bytes = new byte[byteSize];
        long val = fc.size() / byteSize;
        for (long i = 1; i < val; i++) {
            mbuff.get(bytes);
        }
        ummap(mbuff);
        System.out.println(System.currentTimeMillis() - start); // 335ms、321ms、328ms、346ms
    }

    public void ummap(MappedByteBuffer mappedByteBuffer) {
        Cleaner cl = ((DirectBuffer) mappedByteBuffer).cleaner();
        if (cl != null) {
            cl.clean();
        }
    }

    @Test
    public void testCost1() throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader reader = new BufferedReader(new FileReader("dict.txt"));
        String line;
        while (reader.readLine() != null) {
            //
        }
        reader.close();
        System.out.println(System.currentTimeMillis() - start); //3433 ms、3842ms
    }
}
