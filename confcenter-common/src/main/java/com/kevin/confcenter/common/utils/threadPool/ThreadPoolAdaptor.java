package com.kevin.confcenter.common.utils.threadPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 实现任务的代理
 */
public class ThreadPoolAdaptor<T> implements AsynchronousHandler<T> {

    private AsynchronousHandler<T> handler;

    private Future<T> future;

    private T result;

    public ThreadPoolAdaptor(AsynchronousHandler<T> handler) {
        this.handler = handler;
    }

    public AsynchronousHandler getHandler() {
        return handler;
    }

    public Future<T> getFuture() {
        return future;
    }

    public void setFuture(Future<T> future) {
        this.future = future;
    }

    public T getResult() throws ExecutionException, InterruptedException {
        return future.get();
    }

    @Override
    public T call() throws Exception {
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
