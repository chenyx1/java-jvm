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

    public WorkTask(Integer synObjectA, Integer synObjectB) {
        this.synObjectA = synObjectA;
        this.synObjectB = synObjectB;
    }

    @Override
    public  Object call() throws Exception {
        String reult = "";
        synchronized(synObjectA) {
            synchronized (synObjectB) {
                reult = "线程未死锁";
                int rs = synObjectA + synObjectB;
                System.out.println("线程执行中.....rs = :" +rs);
            }
        }

        return reult;
    }
}
