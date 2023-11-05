package org.javen.framework.context

import org.javen.framework.core.concurrent.scheduling.ThreadPoolManager
import org.javen.framework.core.env.Environment
import java.util.UUID
import kotlin.reflect.KClass

abstract class AbstractApplicationContext(activeProfileProvider: ActiveProfileProvider) : ApplicationContext {

    companion object {
        const val THREAD_POOL_MANAGER_BEAN_NAME = "threadPoolManager"

        fun getThreadPoolManagerClassName(): String {
            return ThreadPoolManager::class.java.name
        }
    }

    private val _activeProfile: String
    private val _environment: Environment // prevent kotlin generate getter for it.
    private val _applicationId: String

    private var threadPoolManager: ThreadPoolManager? = null

    init {
        this._activeProfile = activeProfileProvider.getActiveProfile()
        this._environment = Environment.getInstance(_activeProfile)!!
        this._applicationId = generateAppId()
    }

    override fun getId(): String {
        return this._applicationId
    }

    override fun getProfileActivated(): String {
        return this._activeProfile
    }

    override fun getBean(name: String): InstanceObject {
        return getBeanFactory().getBean(name)
    }

    override fun getEnvironment(): Environment {
        return this._environment
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