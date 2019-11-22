package com.chenyx.thread;

import java.util.concurrent.Callable;

/**
 * @desc 自定义线程，用于排查线程死锁
 * @auhtor chenyx
 * @date 2019-11-21
 *
 * */
public class WorkTask implements Callable<Object> {

    private Integer synObjectA;
    private Integer synObjectB;
    private Integer currentNum;


    public WorkTask(Integer synObjectA, Integer synObjectB, Integer currentNum) {
        this.synObjectA = synObjectA;
        this.synObjectB = synObjectB;
        this.currentNum = currentNum;
    }

    @Override
    public  Object call() throws Exception {
        String reult = "";
        synchronized(synObjectA) {
            synchronized (synObjectB) {
                reult = "线程未死锁";
                Thread.sleep(3000);
                int rs = synObjectA + synObjectB;
                System.out.println("当前线程为第"+currentNum+"个线程执行中.....rs = :" +rs);
            }
        }

        return reult;
    }
}
