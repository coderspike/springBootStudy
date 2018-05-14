package com.example.demo;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Test
    public void contextLoads() {
        ExecutorService es = Executors.newFixedThreadPool(2);
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>();
        for (int i = 1; i <= 10; i++) {
            deque.add(i + "");
        }
        es.submit(new Runnable() {
            @Override
            public void run() {
                while (!deque.isEmpty()) {
                    System.out.println(deque.poll() + "-" + Thread.currentThread().getName());
                }
            }
        });
        es.submit(new Runnable() {
            @Override
            public void run() {
                while (!deque.isEmpty()) {
                    System.out.println(deque.poll() + "-" + Thread.currentThread().getName());
                }
            }
        });
        try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
