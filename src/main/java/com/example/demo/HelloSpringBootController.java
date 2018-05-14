package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@RestController
public class HelloSpringBootController {
    @RequestMapping("/sayHello")
    public String sayHello() {
        String result = null;
        try {
            result = this.poolTest();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
    线程池测试
     */
    public String poolTest() throws InterruptedException {
        String result = "";
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < list.size(); i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    show(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("finally");
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();//当变为0时会立即调用
        System.out.println("done");
        result = "done";
        executorService.shutdownNow();
        return result;
    }

    private void show(Integer count) throws InterruptedException {
        Thread.sleep(1200);
        System.out.println(count);
        Thread.sleep(1200);
    }
}

