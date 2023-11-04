package org.javen.framework.core.concurrent

import org.springframework.util.concurrent.FutureUtils
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.concurrent.Future

interface AsyncTaskExecutor : Executor {
    fun submit(task: Runnable): Future<*>

    fun <T> submit(task: Callable<T>): Future<T>

    fun submitCompletable(task: Runnable): CompletableFuture<Void> {
        return CompletableFuture.runAsync(task, this)
    }

    fun <T> submitCompletable(task: Callable<T>): CompletableFuture<T> {
        return FutureUtils.callAsync(task, this)
    }
}