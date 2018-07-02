package com.kevin.confcenter.common.utils;

import com.kevin.confcenter.common.bean.vo.ThreadPoolParameterVO;
import org.apache.commons.configuration.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/7/2 15:42
 */
public final class ThreadPoolUtil {

    private static final String FILE_NAME = "thread_pool_config.properties";

    private static ThreadPoolExecutor executor = getThreadPool();

    /**
     * 线程工厂
     */
    private static class TaskThreadFactory implements ThreadFactory {
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        TaskThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement());
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    private ThreadPoolUtil() {

    }

    /**
     * 提交任务
     * @param task
     * @param <V>
     * @return
     */
    public static <V> Future<V> submit(Callable<V> task) {
        return executor.submit(task);
    }

    /**
     * 执行任务
     * @param task
     */
    public static void execute(Runnable task) {
        executor.execute(task);
    }

    /**
     * 关闭线程池
     */
    public static void shutdown() {
        executor.shutdown();
    }

    /**
     * 停止线程池
     */
    public static void stop() {
        executor.shutdownNow();
    }

    /**
     * 获取线程池对象
     */
    private static ThreadPoolExecutor getThreadPool() {
        ThreadPoolParameterVO vo = initConfig();
        int corePoolSize = vo.getCorePoolSize();
        int maximumPoolSize = vo.getMaximumPoolSize();
        int initialCapacity = vo.getInitialCapacity();
        long keepAliveTime = vo.getKeepAliveTime();
        TimeUnit unit = vo.getUnit();
        String threadName = vo.getThreadName();
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(initialCapacity);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, unit,
                workQueue, new TaskThreadFactory(threadName));
        return executor;
    }

    /**
     * 加载配置，没有就都用默认的
     *
     * @return
     */
    private static ThreadPoolParameterVO initConfig() {
        ThreadPoolParameterVO vo = new ThreadPoolParameterVO();
        if(!CommonConfigUtil.isExist(FILE_NAME)) {
            return vo;
        }
        Configuration configuration = CommonConfigUtil.getConfig(FILE_NAME);
        vo.setCorePoolSize(configuration.getInteger("corePoolSize", vo.getCorePoolSize()));
        vo.setMaximumPoolSize(configuration.getInteger("maximumPoolSize", vo.getMaximumPoolSize()));
        vo.setKeepAliveTime(configuration.getLong("keepAliveTime", vo.getKeepAliveTime()));
        vo.setInitialCapacity(configuration.getInteger("initialCapacity", vo.getInitialCapacity()));
        vo.setThreadName(configuration.getString("threadName", vo.getThreadName()));
        return vo;
    }

}
