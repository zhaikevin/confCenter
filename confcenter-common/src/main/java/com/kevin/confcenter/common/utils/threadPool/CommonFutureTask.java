package com.kevin.confcenter.common.utils.threadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Description: callable包装类
 */
public class CommonFutureTask<V> extends FutureTask<V> {

    private AsynchronousHandler<V> handler;

    public CommonFutureTask(Callable<V> callable) {
        super(callable);
        if (callable instanceof AsynchronousHandler) {
            this.handler = (AsynchronousHandler) callable;
        }


    }

    public CommonFutureTask(Runnable runnable, V result) {
        super(runnable, result);

    }

    public AsynchronousHandler<V> getHandler() {
        return handler;
    }

}
