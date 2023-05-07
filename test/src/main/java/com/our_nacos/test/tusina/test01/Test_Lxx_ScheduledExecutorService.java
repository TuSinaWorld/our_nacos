package com.our_nacos.test.tusina.test01;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class Test_Lxx_ScheduledExecutorService {

    public static ScheduledExecutorService executorService;
    public static void main(String[] args) {
        executorService = new ScheduledThreadPoolExecutor(24, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(false);
            thread.setName("mytask");
            return thread;
        });

        executorService.schedule(new Test_Lxx_ScheduledExecutorService().new MyTask(),5000, TimeUnit.MILLISECONDS);
    }

    class MyTask implements Runnable{

        @Override
        public void run() {
            System.out.println("进程已经执行...");
            executorService.schedule(new Test_Lxx_ScheduledExecutorService().new MyTask(),5000, TimeUnit.MILLISECONDS);
        }
    }
}
