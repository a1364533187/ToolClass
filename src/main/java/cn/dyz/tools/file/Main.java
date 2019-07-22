package cn.dyz.tools.file;

/**
 * Create by suzhiwu on 2019/7/22
 */
public class Main {

    public static void main(String[] args) {
        BigFileReader.Builder builder = new BigFileReader.Builder("d:/reliability.txt",
                new IHandle() {
                    public void handle(String line) {
                        //System.out.println(line);
                        //increat();
                    }
                });
        builder.withTreahdSize(10).withCharset("gbk").withBufferSize(1024 * 1024);
        BigFileReader bigFileReader = builder.build();
        bigFileReader.start();
    }

}
