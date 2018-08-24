package com.example.test;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class ThreadPoolDemo {
    //test.java
    volatile int finishState = 0;


    @Test
    public void test4() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 7, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10));
        ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService(threadPoolExecutor);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    String name = "name_" + i;
                    TestCallable testCallable = new TestCallable(name);
                    try {
                        executorCompletionService.submit(testCallable);

                        synchronized ("AAA") {
                            System.out.print("+++添加任务 name: " + name);
                            System.out.print(" ActiveCount: " + threadPoolExecutor.getActiveCount());
                            System.out.print(" poolSize: " + threadPoolExecutor.getPoolSize());
                            System.out.print(" queueSize: " + threadPoolExecutor.getQueue().size());
                            System.out.println(" taskCount: " + threadPoolExecutor.getTaskCount());
                        }
                    } catch (RejectedExecutionException e) {
                        synchronized ("AAA") {
                            System.out.println("拒绝：" + name);
                        }
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                finishState = 1;
            }
        };

        Thread addThread = new Thread(runnable);
        addThread.start();

        //System.out.println(" taskCount: " + threadPoolExecutor.getTaskCount());

        //添加的任务有被抛弃的。taskCount不一定等于添加的任务。
        int completeCount = 0;
        while (!(completeCount == threadPoolExecutor.getTaskCount() && finishState == 1)) {
            Future<String> take = executorCompletionService.take();
            String taskName = take.get();
            synchronized ("AAA") {
                System.out.print("---完成任务 name: " + taskName);
                System.out.print(" ActiveCount: " + threadPoolExecutor.getActiveCount());
                System.out.print(" poolSize: " + threadPoolExecutor.getPoolSize());
                System.out.print(" queueSize: " + threadPoolExecutor.getQueue().size());
                System.out.print(" taskCount: " + threadPoolExecutor.getTaskCount());
                System.out.println(" finishTask：" + (++completeCount));

            }
        }

        addThread.join();


        while (threadPoolExecutor.getPoolSize() > 0) {
            Thread.sleep(1000);
            synchronized ("AAA") {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                System.out.print(simpleDateFormat.format(new Date()));
                //System.out.print("name: " + taskName);
                System.out.print(" ActiveCount: " + threadPoolExecutor.getActiveCount());
                System.out.print(" poolSize: " + threadPoolExecutor.getPoolSize());
                System.out.print(" queueSize: " + threadPoolExecutor.getQueue().size());
                System.out.println(" taskCount: " + threadPoolExecutor.getTaskCount());
            }
        }

        // Tell threads to finish off.
        threadPoolExecutor.shutdown();
        // Wait for everything to finish.
        while (!threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("complete");
        }

    }
}
