
package com.kevin.confcenter.common.utils.threadPool;

import com.kevin.confcenter.common.utils.CommonConfigUtil;
import com.kevin.confcenter.common.utils.DateUtil;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 异步执行公共线程池
 */
public final class CommonThreadPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonThreadPool.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String FILE_NAME = "thread_pool_config.properties";

    private static ExecutorService execute;

    private static final long EXECUTETIME = 10000L;

    private static volatile CommonThreadPool instance;

    private static Object lock = new Object();

    /**
     * 单例
     *
     * @return
     */
    public static CommonThreadPool getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    execute = getThreadPool();
                    instance = new CommonThreadPool();
                }
            }
        }
        return instance;
    }

    /**
     * 获取线程池对象
     */
    private static ThreadPoolExecutorExtend getThreadPool() {
        ThreadPoolParameterVO vo = initConfig();
        int corePoolSize = vo.getCorePoolSize();
        int maximumPoolSize = vo.getMaximumPoolSize();
        int initialCapacity = vo.getInitialCapacity();
        long keepAliveTime = vo.getKeepAliveTime();
        TimeUnit unit = vo.getUnit();
        String threadName = vo.getThreadName();

        //增加构造队列容量参数
        TaskQueue taskqueue = new TaskQueue(initialCapacity);
        ThreadPoolExecutorExtend executeNew = new ThreadPoolExecutorExtend(corePoolSize, maximumPoolSize,
                keepAliveTime, unit,
                taskqueue, new TaskThreadFactory(threadName), new ThreadPlloRejectedExecutionHandler());

        taskqueue.setParent(executeNew);
        return executeNew;
    }

    /**
     * 加载配置，没有就都用默认的
     *
     * @return
     */
    private static ThreadPoolParameterVO initConfig() {
        ThreadPoolParameterVO vo = new ThreadPoolParameterVO();
        Configuration configuration = CommonConfigUtil.getConfig(FILE_NAME);
        if (configuration == null) {
            return vo;
        }
        vo.setCorePoolSize(configuration.getInteger("corePoolSize", vo.getCorePoolSize()));
        vo.setMaximumPoolSize(configuration.getInteger("maximumPoolSize", vo.getMaximumPoolSize()));
        vo.setKeepAliveTime(configuration.getLong("keepAliveTime", vo.getKeepAliveTime()));
        vo.setInitialCapacity(configuration.getInteger("initialCapacity", vo.getInitialCapacity()));
        vo.setThreadName(configuration.getString("threadName", vo.getThreadName()));
        return vo;
    }

    /**
     * 异步执行公共执行方法
     */
    public <T> ThreadPoolAdaptor<T> execute(AsynchronousHandler<T> command) {
        ThreadPoolAdaptor<T> handler = new ThreadPoolAdaptor(command);
        Future<T> future = execute.submit(handler);
        handler.setFuture(future);
        return handler;

    }

    /**
     * 关闭线程池，执行中的任务可以继续执行
     */
    public boolean shutdown() {
        if (execute != null) {
            execute.shutdown();
            return true;
        }
        return false;
    }

    /**
     * 关闭线程池
     *
     * @return 未执行完的任务列表
     */
    public List<Runnable> stop() {
        return execute.shutdownNow();
    }

    /**
     * 线程工厂
     */
    static class TaskThreadFactory implements ThreadFactory {
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
            t.setDaemon(true);
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    /**
     * 自定义线程创建方法
     */
    static class TaskQueue extends LinkedBlockingQueue<Runnable> {
        /**
         *
         */
        private static final long serialVersionUID = -3966913824895982184L;
        ThreadPoolExecutorExtend parent = null;

        public TaskQueue() {
            super();
        }

        public TaskQueue(int initialCapacity) {
            super(initialCapacity);
        }


        public TaskQueue(Collection<? extends Runnable> c) {
            super(c);
        }

        public void setParent(ThreadPoolExecutorExtend tp) {
            parent = tp;
        }

        public boolean offer(Runnable o) {

            //we can't do any checks
            if (parent == null) {
                return super.offer(o);
            }

            //we are maxed out on threads, simply queue the object
            if (parent.getPoolSize() == parent.getMaximumPoolSize()) {
                return super.offer(o);
            }
            //we have idle threads, just add it to the queue
            //note that we don't use getActiveCount(), see BZ 49730
            AtomicInteger submittedTasksCountNew = parent.submittedTasksCount;
            if (submittedTasksCountNew != null && submittedTasksCountNew.get() <= parent.getPoolSize()) {
                return super.offer(o);
            }
            //if we have less threads than maximum force creation of a new thread
            if (parent.getPoolSize() < parent.getMaximumPoolSize()) {
                return false;
            }

            //if we reached here, we need to add it to the queue
            return super.offer(o);
        }

        public boolean superOffer(Runnable o) throws InterruptedException {

            return super.offer(o);
        }

    }

    /**
     * 自定义线程池任务终止实现
     */
    static class ThreadPlloRejectedExecutionHandler implements RejectedExecutionHandler {

        public ThreadPlloRejectedExecutionHandler() {
        }

        @SuppressWarnings("rawtypes")
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            //判断是不是并发情况导致的失败
            try {
                boolean reAdd = false;
                BlockingQueue q = executor.getQueue();
                if (q instanceof TaskQueue) {
                    reAdd = ((TaskQueue) q).superOffer(r);
                } else {
                    reAdd = executor.getQueue().offer(r);
                }
                if (reAdd) {
                    return;
                }
            } catch (InterruptedException e) {

            } catch (Throwable e) {
                throw new RejectedExecutionException(e);
            }

            try {
                Class taskClass;
                if (r instanceof CommonFutureTask) {
                    AsynchronousHandler handlerAdaptor = ((CommonFutureTask) r).getHandler();

                    //获取真实的handler ，记录日志
                    AsynchronousHandler handler;
                    if (handlerAdaptor instanceof ThreadPoolAdaptor) {
                        handler = ((ThreadPoolAdaptor) handlerAdaptor).getHandler();
                    } else {
                        handler = handlerAdaptor;
                    }
                    taskClass = handler.getClass();
                } else {
                    taskClass = r.getClass();
                }
                StringBuilder sb = new StringBuilder();
                sb.append("task:").append(taskClass);
                sb.append(";happenTime=").append(DateUtil.dateToString(new Date(), DATE_FORMAT));
                LOGGER.error("CommonThreadPool have reach max size：" + sb.toString());

                if (executor instanceof ThreadPoolExecutorExtend) {
                    ((ThreadPoolExecutorExtend) executor).getSubmittedTasksCount().decrementAndGet();
                }

            } catch (Throwable e) {
                e.printStackTrace();
                throw new RejectedExecutionException(e);
            }
            throw new RejectedExecutionException();
        }
    }
}
