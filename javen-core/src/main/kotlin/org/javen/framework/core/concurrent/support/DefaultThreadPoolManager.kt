package org.javen.framework.core.concurrent.support

import org.javen.framework.core.concurrent.AsyncTaskExecutor
import org.javen.framework.core.concurrent.RunnableType
import org.javen.framework.core.concurrent.ThreadOption
import org.javen.framework.core.concurrent.scheduling.ExecutorHolder
import org.javen.framework.core.concurrent.scheduling.ThreadPoolManager
import org.javen.framework.core.concurrent.scheduling.Trigger
import org.javen.framework.core.concurrent.scheduling.bridge.AbstractSpringTriggerWrapper
import org.javen.framework.core.concurrent.scheduling.bridge.SpringExecutorContainer
import java.time.Duration
import java.time.Instant
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Future
import java.util.concurrent.ScheduledFuture

class DefaultThreadPoolManager(option: ThreadOption) : ThreadPoolManager {

    private val executorHolder: ExecutorHolder

    init {
        this.executorHolder = SpringExecutorContainer(option)
    }

    override fun execute(runnable: Runnable) {
        val defaultType = RunnableType.ASYNC
        val executor = getExecutor(defaultType)
        executor.execute(runnable)
    }

    override fun execute(runnable: Runnable, type: RunnableType) {
        val executor = getExecutor(type)
        executor.execute(runnable)
    }

    override fun submit(task: Runnable): Future<*> {
        val defaultType = RunnableType.ASYNC
        val executorService = getExecutor(defaultType) as AsyncTaskExecutor
        return executorService.submit(task)
    }

    override fun submit(task: Runnable, type: RunnableType): Future<*> {
        val executorService = getExecutor(type) as AsyncTaskExecutor
        return executorService.submit(task)
    }

    override fun <T> submit(task: Callable<T>): Future<T> {
        val defaultType = RunnableType.ASYNC
        val executorService = getExecutor(defaultType) as AsyncTaskExecutor
        return executorService.submit(task)
    }

    override fun <T> submit(task: Callable<T>, type: RunnableType): Future<T> {
        val executorService = getExecutor(type) as AsyncTaskExecutor
        return executorService.submit(task)
    }

    override fun schedule(task: Runnable, trigger: Trigger): ScheduledFuture<*>? {
        val scheduler = executorHolder.getTaskScheduler()
        if (trigger is AbstractSpringTriggerWrapper) {
            return scheduler.schedule(task, trigger.unwrap())
        }
        return null
    }

    override fun schedule(task: Runnable, startTime: Instant): ScheduledFuture<*> {
        val scheduler = executorHolder.getTaskScheduler()
        return scheduler.schedule(task, startTime)
    }

    override fun scheduleAtFixedRate(task: Runnable, startTime: Instant, period: Duration): ScheduledFuture<*> {
        val scheduler = executorHolder.getTaskScheduler()
        return scheduler.scheduleAtFixedRate(task, startTime, period)
    }

    override fun scheduleAtFixedRate(task: Runnable, period: Duration): ScheduledFuture<*> {
        val scheduler = executorHolder.getTaskScheduler()
        return scheduler.scheduleAtFixedRate(task, period)
    }

    override fun scheduleWithFixedDelay(task: Runnable, startTime: Instant, delay: Duration): ScheduledFuture<*> {
        val scheduler = executorHolder.getTaskScheduler()
        return scheduler.scheduleWithFixedDelay(task, startTime, delay)
    }

    override fun scheduleWithFixedDelay(task: Runnable, delay: Duration): ScheduledFuture<*> {
        val scheduler = executorHolder.getTaskScheduler()
        return scheduler.scheduleWithFixedDelay(task, delay)
    }

    private fun getExecutor(type: RunnableType): Executor {
        return when (type) {
            RunnableType.HTTP -> this.executorHolder.getHttpExecutor()
            RunnableType.ASYNC -> this.executorHolder.getAsyncTaskExecutor()
            RunnableType.INDEX -> this.executorHolder.getEntityIndexExecutor()
            RunnableType.SERVICE -> this.executorHolder.getServiceExecutor()
        }
    }
}