package com.chenyx.controller;

import com.chenyx.thread.ThreadPool;
import com.chenyx.thread.WorkTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@RestController
public class JvmController {

    @GetMapping("/cpu_while/{a}")
    public Object cpu_while(@PathVariable("a") Integer a) {
        int count = 1;
        while (true) {
            if (a == 1) {
                break;
            }
            JvmController jvmController = new JvmController();
            System.out.println("count:" + count + ",a=" + a);
        }
        return  "返回cpu_while成功！";
    }


    @GetMapping("/cpu_lock/{a}")
    public Object cpu_lock(@PathVariable("a") Integer a) throws Exception{
        int count = 1;
        if (a <= 0) {
            a = 10;
        }
        Integer num1 = 10;
        Integer num2 = 20;
        ExecutorService executorService = ThreadPool.createThreadPool();
        List<Future<Object>> futures = new ArrayList<>();
        for (int i = 0; i < a ; i++) {
            if (i % 2 == 0) {
                WorkTask workTask = new WorkTask(num1,num2,i);
                Future<Object> future =  executorService.submit(workTask);
                futures.add(future);
            } else {
                WorkTask workTask = new WorkTask(num2,num1,i);
                Future<Object> future =  executorService.submit(workTask);
                futures.add(future);
            }
        }
        //打印执行结果
        for (Future<Object> future : futures){
            Object result = future.get();
            System.out.println(result);
        }
        return  "返回cpu_lock成功！";
    }

}
