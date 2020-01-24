package cn.dyz.tools.file.mainargs;

/**
 * Create by suzhiwu on 2019/02/05
 */
public class MainTest {

    public static void main(String[] args) {
        String env = args[0];
        int jobId = Integer.parseInt(args[1]);
        System.out.println(env + "--->" + jobId);
    }
}
