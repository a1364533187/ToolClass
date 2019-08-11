package cn.dyz.tools.file.ProducerConsumerProblem.divide_and_conquer;

/**
 * Create by suzhiwu on 2019/7/29
 */
public class SumArray {
    public static int sum(int start, int[] arr) {
        if (arr.length <= 0) {
            return 0;
        }
        if (start == arr.length - 1) {
            return arr[start];
        }
        return arr[start] + sum(start + 1, arr);
    }

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};
        System.out.println(sum(0, arr));
    }
}
