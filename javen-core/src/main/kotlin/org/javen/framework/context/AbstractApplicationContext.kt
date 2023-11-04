package org.javen.framework.context

import org.javen.framework.core.concurrent.scheduling.ThreadPoolManager
import org.javen.framework.core.env.Environment
import java.util.UUID
import kotlin.reflect.KClass

abstract class AbstractApplicationContext(activeProfileProvider: ActiveProfileProvider) : ApplicationContext {

    companion object {
        const val THREAD_POOL_MANAGER_BEAN_NAME = "threadPoolManager"
        private val THREAD_POOL_MANAGER_CLASS_NAME = getThreadPoolManagerClassName()

        fun getThreadPoolManagerClassName(): String {
            return ThreadPoolManager::class.java.name
        }
    }

    private val activeProfile: String
    private val environment: Environment
    private val applicationId: String

    private var threadPoolManager: ThreadPoolManager? = null

    init {
        this.activeProfile = activeProfileProvider.getActiveProfile()
        this.environment = Environment.getInstance(activeProfile)!!
        this.applicationId = generateAppId()
    }

    override fun getId(): String {
        return this.applicationId
    }

    override fun getEnvironment(): Environment {
        return this.environment
    }

    override fun getProfileActivated(): String {
        return this.activeProfile
    }

    override fun getBean(name: String): InstanceObject {
        return getBeanFactory().getBean(name)
    }

    override fun <T : InstanceObject> getBean(type: KClass<T>): T {
        return getBeanFactory().getBean(type)
    }

    override fun <T : InstanceObject> getBean(name: String, type: KClass<T>): T {
        return getBeanFactory().getBean(name, type)
    }

    private fun generateAppId(): String {
        return UUID.randomUUID().toString()
    }
}