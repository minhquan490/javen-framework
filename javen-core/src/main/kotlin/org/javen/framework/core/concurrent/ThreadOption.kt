package org.javen.framework.core.concurrent

import java.util.concurrent.ThreadFactory

interface ThreadOption {
    fun getSchedulerPoolSize(): Int

    fun getAsyncExecutorCorePoolSize(): Int

    fun getHttpExecutorCorePoolSize(): Int

    fun getServiceExecutorCorePoolSize(): Int

    fun getIndexExecutorCorePoolSize(): Int

    fun setSchedulerPoolSize(schedulerPoolSize: Int)

    fun setAsyncExecutorCorePoolSize(asyncExecutorCorePoolSize: Int)

    fun setHttpExecutorCorePoolSize(httpExecutorCorePoolSize: Int)

    fun setServiceExecutorCorePoolSize(serviceExecutorCorePoolSize: Int)

    fun setIndexExecutorCorePoolSize(indexExecutorCorePoolSize: Int)

    fun getThreadFactory(): ThreadFactory
}