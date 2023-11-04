package org.javen.framework.context.beans

import org.apache.commons.lang3.StringUtils
import org.javen.framework.context.InstanceObject
import org.javen.framework.core.annotation.Component
import org.javen.framework.core.type.filter.TypeMatcher
import org.javen.framework.utils.UnsafeUtils
import java.util.LinkedList
import kotlin.reflect.KClass
import kotlin.reflect.cast

abstract class AbstractBeanFactory : BeanFactory, InstanceObject {

    override fun <T : InstanceObject> getBean(type: KClass<T>): T {
        val beanName: String = resolveBeanName(type.java)
        return getBean(beanName, type)
    }

    override fun getBean(name: String): InstanceObject {
        return getBean(name, InstanceObject::class)
    }

    override fun <T : InstanceObject> getBean(name: String, type: KClass<T>): T {

        val beanDefinition = getBeanDefinition(name)

        if (beanDefinition.isLazy() && !beanDefinition.isSingleton()) {
            return getLazyBean(type, beanDefinition)
        }

        if (beanDefinition.isLazy() || beanDefinition.isSingleton()) {

            val bean: InstanceObject = if (isBeanExist(name)) {
                doGetBean(name)?.unwrap() ?: throw createUnknownBeanException(name)
            } else {
                createBean(beanDefinition).unwrap()
            }

            val matcher: TypeMatcher = SubClassMatcher(type)
            if (matcher.match(bean::class)) {
                return type.cast(bean)
            } else {
                throw createUnknownBeanException(name)
            }
        }

        return getLazyBean(type, beanDefinition)
    }

    override fun selfCreate(vararg params: Any): InstanceObject {
        return this
    }

    fun resolveBeanName(javaType: Class<*>): String {
        val beanNameResolver = BeanNameResolver(javaType)
        return beanNameResolver.resolve()
    }

    protected abstract fun doGetBean(name: String): Bean<InstanceObject>?

    protected abstract fun isBeanExist(name: String): Boolean

    protected abstract fun getBeanDefinition(name: String): BeanDefinition

    protected abstract fun cacheBean(name: String, bean: Bean<InstanceObject>)

    protected fun createBean(beanDefinition: BeanDefinition): Bean<InstanceObject> {
        val seed: InstanceObject = UnsafeUtils.allocateInstance(beanDefinition.getType()) as InstanceObject
        val createdBean: Bean<InstanceObject> = createBean(seed, beanDefinition)
        if (beanDefinition.isSingleton()) {
            cacheBean(beanDefinition.getBeanName(), createdBean)
        }
        return createdBean
    }

    protected fun createUnknownBeanException(name: String): BeanNotFoundException {
        return BeanNotFoundException("Unknown bean name [${name}]")
    }

    private fun <T : InstanceObject> getLazyBean(type: KClass<T>, beanDefinition: BeanDefinition): T {
        val createdBean: Bean<InstanceObject> = createBean(beanDefinition)
        return type.cast(createdBean.unwrap())
    }

    private fun createBean(seed: InstanceObject, definition: BeanDefinition): Bean<InstanceObject> {
        val requiredBeanTypes: Array<Class<*>> = definition.getPrimaryConstructorParameters()
        val instanceParams: MutableList<Any> = LinkedList()
        for (requiredBeanType in requiredBeanTypes) {
            val beanName = resolveBeanName(requiredBeanType)
            val bean: Bean<InstanceObject>? = doGetBean(beanName)
            bean?.unwrap()?.let { instanceParams.add(it) }
        }

        return InternalBean(seed.selfCreate(instanceParams.toTypedArray()), definition.isSingleton())
    }

    private class SubClassMatcher(private val subClassType: KClass<*>) : TypeMatcher {

        override fun match(target: KClass<*>): Boolean {
            return target.java.isAssignableFrom(subClassType.java)
        }

    }

    private class BeanNameResolver(private val javaClass: Class<*>) {

        fun resolve(): String {
            if (javaClass.isAnnotationPresent(Component::class.java)) {
                val component: Component = javaClass.getAnnotation(Component::class.java)
                val name: String = component.name
                if (StringUtils.isBlank(name)) {
                    return javaClass.name
                }
            }
            return javaClass.name
        }
    }

    private class InternalBean(private val delegate: InstanceObject, private val isSingleton: Boolean) :
        Bean<InstanceObject> {
        override fun unwrap(): InstanceObject {
            return this.delegate
        }

        override fun isSingleton(): Boolean {
            return this.isSingleton
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is InternalBean) return false

            if (delegate != other.delegate) return false

            return true
        }

        override fun hashCode(): Int {
            return delegate.hashCode()
        }

    }
}