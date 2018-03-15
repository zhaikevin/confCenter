
package com.kevin.confcenter.common.utils.threadPool;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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

    public static final String LONG_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private ExecutorService execute;

    private static final long EXECUTETIME = 10000L;

    public CommonThreadPool(Integer poolSize) {
        ThreadPoolParameterVO threadPoolParameterVO = new ThreadPoolParameterVO();
        threadPoolParameterVO.setCorePoolSize(poolSize);
        execute = getThreadPool(new ThreadPoolParameterVO());
    }

    /**
     * 异步执行公共执行方法
     */
    public Future<Object> execute(AsynchronousHandler command) {

        ThreadPoolAdaptor handler = new ThreadPoolAdaptor(command, EXECUTETIME);
        Future<Object> future = execute.submit(handler);

        return future;

    }

    /**
     * 关闭线程池
     */
    public boolean shutDown() {
        if (execute != null) {
            execute.shutdown();
            return true;
        }
        return false;
    }

    /**
     * 获取线程池对象
     */
    private ThreadPoolExecutorExtend getThreadPool(ThreadPoolParameterVO vo) {
        int corePoolSize = vo.getCorePoolSize();
        int maximumPoolSize = vo.getMaximumPoolSize();
        int initialCapacity = vo.getInitialCapacity();
        long keepAliveTime = vo.getKeepAliveTime();
        String threadName = vo.getThreadName();

        //增加构造队列容量参数
        TaskQueue taskqueue = new TaskQueue(initialCapacity, vo.isDiscard());
        ThreadPoolExecutorExtend executeNew = new ThreadPoolExecutorExtend(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS,
                taskqueue, new TaskThreadFactory(threadName), new ThreadPlloRejectedExecutionHandler(vo.isDiscard()));

        taskqueue.setParent(executeNew);
        return executeNew;
    }

    /**
     * 是否大于内存限制的阀值
     *
     * @return
     */
    private static boolean isMemoryThreshold() {

        long size = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
        long thresholdSize = (long) (ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax() * 0.7);
        if (size > thresholdSize) {
            return true;
        }
        return false;
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
        boolean isDiscard = true;

        public TaskQueue() {
            super();
        }

        public TaskQueue(int initialCapacity) {
            super(initialCapacity);
        }

        public TaskQueue(int initialCapacity, boolean isDiscard) {
            super(initialCapacity);
            this.isDiscard = isDiscard;
        }

        public TaskQueue(Collection<? extends Runnable> c) {
            super(c);
        }

        public void setParent(ThreadPoolExecutorExtend tp) {
            parent = tp;
        }

        public boolean force(Runnable o) {
            if (parent.isShutdown()) {
                throw new RejectedExecutionException("Executor not running, can't force a command into the queue");
            }

            return super.offer(o); //forces the item onto the queue, to be used if the task is rejected
        }

        public boolean offer(Runnable o) {
            //we can't do any checks
            if (parent == null) {
                return super.offer(o);
            }

            //内存限制
            if (this.isDiscard && isMemoryThreshold()) {
                return false;
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

        boolean isDiscard = true;

        public ThreadPlloRejectedExecutionHandler() {
        }

        public ThreadPlloRejectedExecutionHandler(boolean isDiscard) {
            this.isDiscard = isDiscard;
        }

        @SuppressWarnings("rawtypes")
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {


            //没有到内存阀值，执行如下
            if (!this.isDiscard || (this.isDiscard && !isMemoryThreshold())) {
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

                    e.printStackTrace();
                } catch (Throwable e) {
                    throw new RejectedExecutionException(e);
                }
            }

            if (r instanceof CommonFutureTask) {
                AsynchronousHandler handlerAdaptor = ((CommonFutureTask) r).getHandler();
                if (handlerAdaptor == null) {
                    System.out.println("CommonThreadPool 以达到队列容量上限：" + r.toString());

                    //lhb to 2015.3.11
                    throw new RejectedExecutionException();
                }
            }

            try {
                if (r instanceof CommonFutureTask) {
                    AsynchronousHandler handlerAdaptor = ((CommonFutureTask) r).getHandler();

                    //获取真实的handler ，记录日志
                    AsynchronousHandler handler = null;
                    if (handlerAdaptor instanceof ThreadPoolAdaptor) {
                        handler = ((ThreadPoolAdaptor) handlerAdaptor).getHandler();
                        if (handler == null) {
                            handler = handlerAdaptor;
                        }
                    } else {
                        handler = handlerAdaptor;
                    }
                    StringBuilder sb = new StringBuilder();

                    sb.append("任务名称:").append(handler.getClass());
                    sb.append("。happenTime=").append(formateDate());
                    sb.append("。toString=").append(handler.toString());
                    System.out.println("CommonThreadPool 以达到队列容量上限：" + sb.toString());

                } else {

                    StringBuilder sb = new StringBuilder();
                    sb.append("任务名称:").append(r.getClass());
                    sb.append("。happenTime=").append(formateDate());
                    sb.append("。toString=").append(r.toString());
                    System.out.println("CommonThreadPool 以达到队列容量上限：" + sb.toString());
                }

                //自定义线程池，执行
                if (executor instanceof ThreadPoolExecutorExtend) {
                    ((ThreadPoolExecutorExtend) executor).getSubmittedTasksCount().decrementAndGet();
                }

            } catch (Throwable e) {
                e.printStackTrace();
                throw new RejectedExecutionException(e);
            }

            //lhb to 2015.3.11
            throw new RejectedExecutionException();
        }

        private String formateDate() {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(LONG_FORMAT);
            String result = sdf.format(date);
            return result;
        }

    }

}
