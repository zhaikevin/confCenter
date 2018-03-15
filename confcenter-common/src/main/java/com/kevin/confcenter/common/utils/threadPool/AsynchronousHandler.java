package com.kevin.confcenter.common.utils.threadPool;

import java.util.concurrent.Callable;

public interface AsynchronousHandler extends Callable<Object> {

    /**
     * 执行完成后，调用的方法
     */
    void executeAfter(Throwable t);

    /**
     * 执行完成后前，调用的方法
     */
    void executeBefore(Thread t);

}
