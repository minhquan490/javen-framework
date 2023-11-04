package org.javen.framework.core.concurrent.scheduling

import org.javen.framework.core.concurrent.AsyncTaskExecutor
import org.springframework.scheduling.TaskScheduler
import java.util.concurrent.Executor

interface ExecutorHolder {
    fun getEntityIndexExecutor(): Executor

    fun getHttpExecutor(): Executor

    fun getServiceExecutor(): Executor

    fun getAsyncTaskExecutor(): AsyncTaskExecutor

    fun getTaskScheduler(): TaskScheduler
}