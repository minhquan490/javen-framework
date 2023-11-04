package org.javen.framework.core.concurrent.scheduling

import org.javen.framework.core.concurrent.RunnableType
import java.time.Duration
import java.time.Instant
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.ScheduledFuture


interface ThreadPoolManager {
    fun execute(runnable: Runnable)

    fun execute(runnable: Runnable, type: RunnableType)

    fun submit(task: Runnable): Future<*>

    fun submit(task: Runnable, type: RunnableType): Future<*>

    fun <T> submit(task: Callable<T>): Future<T>

    fun <T> submit(task: Callable<T>, type: RunnableType): Future<T>

    fun schedule(task: Runnable, trigger: Trigger): ScheduledFuture<*>?

    fun schedule(task: Runnable, startTime: Instant): ScheduledFuture<*>?

    fun scheduleAtFixedRate(task: Runnable, startTime: Instant, period: Duration): ScheduledFuture<*>

    fun scheduleAtFixedRate(task: Runnable, period: Duration): ScheduledFuture<*>

    fun scheduleWithFixedDelay(task: Runnable, startTime: Instant, delay: Duration): ScheduledFuture<*>

    fun scheduleWithFixedDelay(task: Runnable, delay: Duration): ScheduledFuture<*>
}