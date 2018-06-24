
package com.kevin.confcenter.common.utils.threadPool;

import java.util.concurrent.TimeUnit;

/**
 * 线程池参数vo
 */
public class ThreadPoolParameterVO {

    private static final int CORE_POOL_SIZE = 5;

    private static final int MAXIMUM_POOLSIZE = 200;

    private static final int INITIAL_CAPACITY = 1000000;

    private static final int KEEP_ALIVE_TIME = 120;

    /**
     * 核心线程池的数量
     */
    private int corePoolSize = CORE_POOL_SIZE;

    /**
     * 最大线程池的数量
     */
    private int maximumPoolSize = MAXIMUM_POOLSIZE;

    /**
     * 任务队列的容量
     */
    private int initialCapacity = INITIAL_CAPACITY;

    /**
     * 核心线程过期时间
     */
    private long keepAliveTime = KEEP_ALIVE_TIME;

    /**
     * 过期时间单位
     */
    private TimeUnit unit = TimeUnit.SECONDS;

    /**
     * 线程名称
     */
    private String threadName = "conf-center-threadPool-";

    private boolean isDiscard = false;

    public static int getCorePoolSize() {
        return CORE_POOL_SIZE;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public static int getMaximumPoolsize() {
        return MAXIMUM_POOLSIZE;
    }

    public static int getInitialCapacity() {
        return INITIAL_CAPACITY;
    }

    public void setInitialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public static int getKeepAliveTime() {
        return KEEP_ALIVE_TIME;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public boolean isDiscard() {
        return isDiscard;
    }

    public void setDiscard(boolean discard) {
        isDiscard = discard;
    }

    @Override
    public String toString() {
        return "ThreadPoolParameterVO{" +
                "corePoolSize=" + corePoolSize +
                ", maximumPoolSize=" + maximumPoolSize +
                ", initialCapacity=" + initialCapacity +
                ", keepAliveTime=" + keepAliveTime +
                ", unit=" + unit +
                ", threadName='" + threadName + '\'' +
                ", isDiscard=" + isDiscard +
                '}';
    }
}
