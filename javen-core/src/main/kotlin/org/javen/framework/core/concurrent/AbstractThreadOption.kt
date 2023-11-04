package org.javen.framework.core.concurrent

abstract class AbstractThreadOption : ThreadOption {
    private var schedulerPoolSize = 1
    private var asyncExecutorCorePoolSize = 1
    private var httpExecutorCorePoolSize = 1
    private var serviceExecutorCorePoolSize = 1
    private var indexExecutorCorePoolSize = 1

    override fun getSchedulerPoolSize(): Int {
        return schedulerPoolSize
    }

    override fun getAsyncExecutorCorePoolSize(): Int {
        return asyncExecutorCorePoolSize
    }

    override fun getHttpExecutorCorePoolSize(): Int {
        return httpExecutorCorePoolSize
    }

    override fun getServiceExecutorCorePoolSize(): Int {
        return serviceExecutorCorePoolSize
    }

    override fun getIndexExecutorCorePoolSize(): Int {
        return indexExecutorCorePoolSize
    }

    override fun setSchedulerPoolSize(schedulerPoolSize: Int) {
        this.schedulerPoolSize = schedulerPoolSize
    }

    override fun setAsyncExecutorCorePoolSize(asyncExecutorCorePoolSize: Int) {
        this.asyncExecutorCorePoolSize = asyncExecutorCorePoolSize
    }

    override fun setHttpExecutorCorePoolSize(httpExecutorCorePoolSize: Int) {
        this.httpExecutorCorePoolSize = httpExecutorCorePoolSize
    }

    override fun setServiceExecutorCorePoolSize(serviceExecutorCorePoolSize: Int) {
        this.serviceExecutorCorePoolSize = serviceExecutorCorePoolSize
    }

    override fun setIndexExecutorCorePoolSize(indexExecutorCorePoolSize: Int) {
        this.indexExecutorCorePoolSize = indexExecutorCorePoolSize
    }

    companion object {
        fun ofPlatform(): ThreadOption {
            return PlatformThreadOption()
        }

        fun ofVirtual(): ThreadOption {
            return VirtualThreadOption()
        }
    }
}