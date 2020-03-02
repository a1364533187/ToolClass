package com.bigcow.com.cacheline;

/**
 * Create by suzhiwu on 2020/02/09
 */
public class CacheLineTest {

    public static void main(String[] args) {
        //声明一个1000000行8列的long型的数组，则一行为一个cache line
        int row = 1000000;
        long[][] longArr = new long[row][8];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < 8; j++) {
                longArr[i][j] = 0l;
            }
        }

        //使用缓存行: 一行一行的读，会读一个cache line
        long marked = System.currentTimeMillis();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < 8; j++) {
                long sum = +longArr[i][j];
            }
        }
        System.out.println("loop times:" + (System.currentTimeMillis() - marked) + "ms");


        //不使用cache line：跳行读取
        marked = System.currentTimeMillis();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < row; j++) {
                long sum = +longArr[j][i];
            }
        }
        System.out.println("loop times:" + (System.currentTimeMillis() - marked) + "ms");
    }
}
