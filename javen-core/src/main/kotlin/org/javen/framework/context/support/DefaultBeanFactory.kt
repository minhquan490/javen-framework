package org.javen.framework.context.support

import org.javen.framework.context.InstanceObject
import org.javen.framework.context.beans.AbstractBeanFactory
import org.javen.framework.context.beans.Bean
import org.javen.framework.context.beans.BeanDefinition
import org.javen.framework.context.beans.CloseableBeanFactory
import org.javen.framework.core.Component
import org.javen.framework.core.Ignore
import org.javen.framework.core.Ordered
import org.javen.framework.core.Scope
import org.javen.framework.core.type.reader.ClassReader
import org.javen.framework.core.type.reader.ConstructorMetadata
import org.javen.framework.core.type.scanner.ScanResult
import org.javen.framework.core.type.scanner.StereotypeScanner
import java.util.SortedSet
import java.util.TreeSet
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

@Ignore
@Component
class DefaultBeanFactory(scanPackages: Collection<String>) : AbstractBeanFactory(), CloseableBeanFactory {

    private val beanDefinitionMap: MutableMap<String, BeanDefinition> = ConcurrentHashMap()
    private val singletonBeanMap: MutableMap<String, Bean<InstanceObject>> = ConcurrentHashMap()

    init {
        val scanner = StereotypeScanner()

        val scanResult: ScanResult<Collection<KClass<*>>> = scanner.scan(scanPackages)

        val beanDefinitionSet: SortedSet<BeanDefinition> =
            TreeSet(Comparator.comparing { definition -> definition.getOrder() })

        for (type in scanResult.get()!!) {
            val definition = addBeanDefinition(type, false)
            beanDefinitionSet.add(definition)
        }

        for (definition in beanDefinitionSet) {
            if (definition.isSingleton()) {
                createBean(definition)
            }
        }
    }

    override fun doGetBean(name: String): Bean<InstanceObject>? {
        return singletonBeanMap[name]
    }

    override fun isBeanExist(name: String): Boolean {
        return singletonBeanMap.containsKey(name)
    }

    override fun getBeanDefinition(name: String): BeanDefinition {
        return beanDefinitionMap[name] ?: throw createUnknownBeanException(name)
    }

    override fun cacheBean(name: String, bean: Bean<InstanceObject>) {
        if (isBeanExist(name)) {
            throw IllegalArgumentException("Bean [${name}] is existed")
        } else {
            singletonBeanMap[name] = bean
        }
    }


    override fun close() {
        this.beanDefinitionMap.clear()
        this.singletonBeanMap.clear()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DefaultBeanFactory) return false

        if (beanDefinitionMap != other.beanDefinitionMap) return false
        if (singletonBeanMap != other.singletonBeanMap) return false

        return true
    }

    override fun hashCode(): Int {
        var result = beanDefinitionMap.hashCode()
        result = 31 * result + singletonBeanMap.hashCode()
        return result
    }

    fun addBean(name: String, bean: InstanceObject) {
        if (beanDefinitionMap.containsKey(name)) {
            return
        }

        val definition: BeanDefinition = addBeanDefinition(bean::class, true)
        val customBean = CustomBean(bean, definition.getOrder())
        cacheBean(name, customBean)
    }

    private fun addBeanDefinition(type: KClass<*>, isDefault: Boolean): BeanDefinition {
        val definition = createDefinition(type, isDefault)
        beanDefinitionMap[definition.getBeanName()] = definition
        return definition
    }

    private fun createDefinition(type: KClass<*>, isDefault: Boolean): BeanDefinition {
        val javaType: Class<*> = type.java
        val reader: ClassReader = ClassReader.forType(type)
        val component: Component = javaType.getAnnotation(Component::class.java)
            ?: throw IllegalArgumentException("Bean [${javaType.name}] for create definition not a component of application")

        val definition =
            BeanDefinition(
                reader.readClass(),
                resolveBeanName(javaType),
                component,
                javaType.getAnnotation(Scope::class.java)
            )

        if (isDefault) {
            definition.setOrder(Ordered.HIGHEST)
            return definition
        }

        val primaryConstructor: ConstructorMetadata? = definition.getConstructorPrimary()
        if (primaryConstructor == null || primaryConstructor.paramTypes.isEmpty()) {
            definition.setOrder(Ordered.HIGHEST)
        }
        return definition
    }

    private class CustomBean(private val wrappedObject: InstanceObject, private val order: Int) : Bean<InstanceObject> {

        override fun unwrap(): InstanceObject {
            return this.wrappedObject
        }

        override fun isSingleton(): Boolean {
            return true
        }

        override fun getOrder(): Int {
            return this.order
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is CustomBean) return false

            if (wrappedObject != other.wrappedObject) return false
            if (order != other.order) return false

            return true
        }

        override fun hashCode(): Int {
            var result = wrappedObject.hashCode()
            result = 31 * result + order
            return result
        }
    }
}