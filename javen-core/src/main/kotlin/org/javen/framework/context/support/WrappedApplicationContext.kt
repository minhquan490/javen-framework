package org.javen.framework.context.support

import org.javen.framework.context.InstanceObject
import org.javen.framework.context.ListenableApplicationContext
import org.javen.framework.context.beans.BeanFactory
import org.javen.framework.context.event.ApplicationEvent
import org.javen.framework.core.env.Environment
import org.springframework.context.ApplicationContext
import java.util.Locale
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.cast

class WrappedApplicationContext(private val springContext: ApplicationContext) : ListenableApplicationContext {

    private val environment: Environment
    private val profileName: String

    init {
        var profileName: String = springContext.environment.activeProfiles[0]
        if (profileName == "default") {
            profileName = "local"
        }
        this.environment = Environment.getInstance(profileName)!!
        this.profileName = profileName
    }

    override fun start() {
        // Do nothing
    }

    override fun getTotalStartTimeInMillisecond(): Long {
        return springContext.startupDate
    }

    override fun getId(): String {
        return springContext.id ?: UUID.randomUUID().toString()
    }

    override fun getEnvironment(): Environment {
        return this.environment
    }

    override fun getProfileActivated(): String {
        return this.profileName
    }

    override fun getBean(name: String): InstanceObject {
        return springContext.getBean(name) as InstanceObject
    }

    override fun <T : InstanceObject> getBean(type: KClass<T>): T {
        return springContext.getBean(type.java)
    }

    override fun <T : InstanceObject> getBean(name: String, type: KClass<T>): T {
        val bean = getBean(name)
        return type.cast(bean)
    }

    override fun getBeanFactory(): BeanFactory {
        return WrappedBeanFactory(this.springContext.autowireCapableBeanFactory)
    }

    override fun fireEvent(event: ApplicationEvent) {
        return springContext.publishEvent(event)
    }

    override fun getMessage(code: Int): String {
        return getMessage(code, Locale.getDefault())
    }

    override fun getMessage(code: Int, locale: Locale): String {
        return getMessage(code, locale, null)
    }

    override fun getMessage(code: Int, locale: Locale, params: Array<Any>?): String {
        return springContext.getMessage(code.toString(), params, locale)
    }

    override fun close() {
        // Do nothing
    }

    private class WrappedBeanFactory(private val springBeanFactory: org.springframework.beans.factory.BeanFactory) :
        BeanFactory {
        override fun getBean(name: String): InstanceObject {
            return springBeanFactory.getBean(name) as InstanceObject
        }

        override fun <T : InstanceObject> getBean(type: KClass<T>): T {
            return springBeanFactory.getBean(type.java)
        }

        override fun <T : InstanceObject> getBean(name: String, type: KClass<T>): T {
            return springBeanFactory.getBean(name, type.java)
        }
    }
}