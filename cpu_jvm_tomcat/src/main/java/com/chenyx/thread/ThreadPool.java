package com.chenyx.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @desc 自定义线程连接池
 * @author chenyx
 * @date 2019-11-21
 *
 * */
public class ThreadPool {
    public static final Logger LOGGER = LoggerFactory.getLogger(ThreadPool.class);

    private static final Integer CORE_THREAD_NUM = 30;//核心线程数
    private static final Integer MAX_THREAD_NUM = 300;//最大线程数
    private static final Long KEEP_ALIVE_TIME = 60L;//线程存活时间
    private static ReentrantLock reentrantLock = new ReentrantLock();//重入锁
    private static  ThreadPoolExecutor threadPoolExecutor = null;

    /**
     * @desc 创建线程池
     * @author chenyx
     * @date 2019-11-21
     * */
    public static ThreadPoolExecutor createThreadPool() {
        try {
            //双重检测机制，防止高并发创建线程池
            if (threadPoolExecutor == null) {
                reentrantLock.lock();
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = new ThreadPoolExecutor(CORE_THREAD_NUM,MAX_THREAD_NUM,KEEP_ALIVE_TIME,
                            TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());
                }
            }
        }catch (Exception e) {
            LOGGER.error("创建线程池异常！");
        }finally {
            if (reentrantLock.isLocked()) {
                reentrantLock.unlock();
            }
        }
        return threadPoolExecutor;
    }
}
