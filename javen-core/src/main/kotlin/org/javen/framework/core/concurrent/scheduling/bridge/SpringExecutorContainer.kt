package org.javen.framework.core.concurrent.scheduling.bridge

import org.javen.framework.core.concurrent.AsyncTaskExecutor
import org.javen.framework.core.concurrent.ThreadOption
import org.javen.framework.core.concurrent.scheduling.ExecutorHolder
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Future

class SpringExecutorContainer(val option: ThreadOption) : ExecutorHolder {

    private var entityIndexExecutor: ThreadPoolTaskExecutor? = null
    private var httpExecutor: ThreadPoolTaskExecutor? = null
    private var serviceExecutor: ThreadPoolTaskExecutor? = null
    private var asyncExecutor: ThreadPoolTaskExecutor? = null
    private var taskScheduler: ThreadPoolTaskScheduler? = null

    override fun getEntityIndexExecutor(): Executor {
        if (entityIndexExecutor == null) {
            this.entityIndexExecutor = createThreadPoolExecutor(this.option.getIndexExecutorCorePoolSize())
        }
        return this.entityIndexExecutor!!
    }

    override fun getHttpExecutor(): Executor {
        if (httpExecutor == null) {
            this.httpExecutor = createThreadPoolExecutor(this.option.getHttpExecutorCorePoolSize())
        }
        return this.httpExecutor!!
    }

    override fun getServiceExecutor(): Executor {
        if (serviceExecutor == null) {
            this.serviceExecutor = createThreadPoolExecutor(this.option.getServiceExecutorCorePoolSize())
        }
        return this.serviceExecutor!!
    }

    override fun getAsyncTaskExecutor(): AsyncTaskExecutor {
        if (asyncExecutor == null) {
            this.asyncExecutor = createThreadPoolExecutor(this.option.getAsyncExecutorCorePoolSize())
        }
        return WrappedAsyncTaskExecutor(this.asyncExecutor!!)
    }

    override fun getTaskScheduler(): TaskScheduler {
        if (taskScheduler == null) {
            this.taskScheduler = createTaskScheduler(this.option.getSchedulerPoolSize())
        }
        return this.taskScheduler!!
    }

    private fun createThreadPoolExecutor(coreSize: Int): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = coreSize
        executor.initialize()
        executor.setThreadFactory(option.getThreadFactory())
        return executor
    }

    private fun createTaskScheduler(coreSize: Int): ThreadPoolTaskScheduler {
        val scheduler = ThreadPoolTaskScheduler()
        scheduler.setPoolSize(coreSize)
        scheduler.initialize()
        scheduler.setThreadFactory(option.getThreadFactory())
        return scheduler
    }

    private class WrappedAsyncTaskExecutor(private val asyncExecutor: ThreadPoolTaskExecutor) : AsyncTaskExecutor {
        override fun submit(task: Runnable): Future<*> {
            return asyncExecutor.submit(task)
        }

        override fun <T> submit(task: Callable<T>): Future<T> {
            return asyncExecutor.submit(task)
        }

        override fun execute(command: Runnable) {
            asyncExecutor.submit(command)
        }

    }
}