package com.kevin.confcenter.common.utils.threadPool;

import java.util.concurrent.Future;

/**
 * 实现任务的代理
 */
public class ThreadPoolAdaptor implements AsynchronousHandler {

    private AsynchronousHandler handler;

    private Future<Object> future;

    private final long executeTime;

    public ThreadPoolAdaptor(AsynchronousHandler handler, long time) {
        this.handler = handler;
        executeTime = System.currentTimeMillis() + time;
    }

    public AsynchronousHandler getHandler() {
        return handler;
    }

    Future<Object> getFuture() {
        return future;
    }

    void setFuture(Future<Object> future) {
        this.future = future;
    }

    long getExecuteTime() {

        return executeTime;
    }

    @Override
    public Object call() throws Exception {
        return handler.call();
    }

    @Override
    public void executeAfter(Throwable t) {
        handler.executeAfter(t);
    }

    @Override
    public void executeBefore(Thread t) {
        handler.executeBefore(t);
    }
}
