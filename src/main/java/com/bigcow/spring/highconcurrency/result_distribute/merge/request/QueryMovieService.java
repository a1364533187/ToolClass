package com.bigcow.spring.highconcurrency.result_distribute.merge.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class QueryMovieService {

    //并发安全的阻塞队列，积攒请求。（每隔N毫秒批量处理一次）
    LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue();

    // 定时任务的实现,每隔开N毫秒处理一次数据。
    public void init() {
        // 定时任务线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        // 立即执行任务，并间隔10 毫秒重复执行。
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                //1.从阻塞队列中取出queue的请求，生成一次批量查询。
                int size = queue.size();
                if (size == 0) {
                    return;
                }
                List<Request> requests = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    // 移出队列，并返回。
                    Request poll = queue.poll();
                    requests.add(poll);
                }
                //2.组装一个批量查询请求参数。
                List<String> movieCodes = new ArrayList<>();
                for (Request request : requests) {
                    movieCodes.add(request.getMovieCode());
                }
                //3. http 请求，或者 dubbo 请求。批量请求，得到结果list。
                System.out.println("本次合并请求数量：" + movieCodes.size());
                //查询出批量查询的结果
                Thread.sleep(1000);
                List<Map<String, String>> responses = new ArrayList<Map<String, String>>() {

                    {
                        add(new HashMap<String, String>() {

                            {
                                put("code1", "haha1");
                            }
                        });
                        add(new HashMap<String, String>() {

                            {
                                put("code2", "haha2");
                            }
                        });
                        add(new HashMap<String, String>() {

                            {
                                put("code3", "haha3");
                            }
                        });
                    }
                };

                //4.把list转成map方便快速查找。
                Map<String, String> responseMap = new HashMap<>();
                for (Map<String, String> response : responses) {
                    responseMap.putAll(response);
                }

                //4.将结果响应给每一个单独的用户请求。
                for (Request request : requests) {
                    //根据请求中携带的能表示唯一参数，去批量查询的结果中找响应。
                    String result = responseMap.get(request.getMovieCode());

                    //将结果返回到对应的请求线程。2个线程通信，异步编程赋值。
                    //complete(),源码注释翻译：如果尚未完成，则将由方法和相关方法返回的值设置为给定值
                    request.getFuture().complete(new HashMap<String, String>() {

                        {
                            put(request.getMovieCode(), result);
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }, 10, 10, TimeUnit.MILLISECONDS);

    }

    // 1万个用户请求，1万个并发,查询电影信息
    public Map<String, Object> queryMovie(String movieCode)
            throws ExecutionException, InterruptedException, ExecutionException {
        //请求合并，减少接口调用次数,提升性能。
        //思路：将不同用户的同类请求，合并起来。
        //并非立刻发起接口调用，请求 。是先收集起来，再进行批量请求。
        Request request = new Request();
        //请求参数
        request.setMovieCode(movieCode);
        //异步编程，创建当前线程的任务，由其他线程异步运算，获取异步处理的结果。
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        request.setFuture(future);

        //请求参数放入队列中。定时任务去消化请求。
        queue.add(request);
        System.out.println("--->queue size" + queue.size());
        //阻塞等待获取结果。
        Map<String, Object> stringObjectMap = future.get();
        return stringObjectMap;
    }
}
