package cn.dyz.tools.file.multifile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Create by suzhiwu on 2019/
 */
public class handleMultiFile {

    private ExecutorService readExcutorService;

    //<String, AtomicBoolean> -> key 文件读入的路径， value 文件是否被多线程访问过
    private ConcurrentHashMap<String, AtomicBoolean> pathIsVisited = new ConcurrentHashMap<String, AtomicBoolean>();

    public void handle(String path) {
        //获取path 下的文件数目
        final List<String> filePaths = new ArrayList<String>();
        getFilePath(path, filePaths);
        for (String filePath : filePaths) {
            pathIsVisited.put(filePath, new AtomicBoolean(false));
        }
        readExcutorService = Executors.newFixedThreadPool(filePaths.size());
        readExcutorService.execute(new Runnable() { //异步执行，执行完没有，主线程不知道
            public void run() {
                for (String filePath : filePaths) {
                    if (pathIsVisited.get(filePath).get() == false) {
                        //TODO 读文件
                        Runtime.getRuntime().availableProcessors();

                        pathIsVisited.put(filePath, new AtomicBoolean(true));
                    }
                }
            }
        });

    }

    public void getFilePath(String path, List<String> filePaths) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    System.out.println("目录：" + files[i].getPath());
                    getFilePath(files[i].getPath(), filePaths);
                } else {
                    System.out.println("文件：" + files[i].getPath());
                    filePaths.add(files[i].getPath());
                }
            }
        } else {
            System.out.println("文件：" + file.getPath());
            filePaths.add(file.getPath());
        }
    }
}
