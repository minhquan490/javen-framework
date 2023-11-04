package org.javen.framework.context

import org.javen.framework.context.beans.BeanFactory
import org.javen.framework.context.beans.CloseableBeanFactory
import org.javen.framework.context.event.ApplicationCloseEvent
import org.javen.framework.context.event.ApplicationEventMultiCaster
import org.javen.framework.context.event.ApplicationRefreshEvent
import org.javen.framework.context.event.ApplicationStartEvent
import org.javen.framework.context.support.ClasspathActiveProfileProvider
import org.javen.framework.context.support.DefaultBeanFactory
import org.springframework.util.StopWatch

abstract class AbstractListenableApplicationContext(private val basePackages: Collection<String>) :
    AbstractApplicationPublisher(ClasspathActiveProfileProvider(), basePackages),
    ListenableApplicationContext, InstanceObject {

    private var beanFactory: BeanFactory? = null
    private var stopWatch: StopWatch = StopWatch()

    override fun start() {
        stopWatch.start()
        fireEvent(ApplicationStartEvent())
        if (beanFactory == null) {
            this.beanFactory = DefaultBeanFactory(basePackages)
        }
        stopWatch.stop()
        addDefaultBean()
        fireEvent(ApplicationRefreshEvent())
    }

    private fun addDefaultBean() {
        val defaultBeanFactory: DefaultBeanFactory = this.beanFactory as DefaultBeanFactory
        defaultBeanFactory.addBean(ApplicationContext::class.java.name, this)
        defaultBeanFactory.addBean(BeanFactory::class.java.name, defaultBeanFactory)
        defaultBeanFactory.addBean(ApplicationEventMultiCaster::class.java.name, this.caster)
    }

    override fun getTotalStartTimeInMillisecond(): Long {
        return stopWatch.totalTimeMillis
    }

    override fun getBeanFactory(): BeanFactory {
        return this.beanFactory ?: throw IllegalStateException("Unable retrieve BeanFactory because it init failure")
    }

    override fun close() {
        fireEvent(ApplicationCloseEvent())
        if (this.beanFactory != null && this.beanFactory is CloseableBeanFactory) {
            (this.beanFactory as CloseableBeanFactory).close()
        }
    }

    override fun selfCreate(vararg params: Any): InstanceObject {
        return this
    }
}