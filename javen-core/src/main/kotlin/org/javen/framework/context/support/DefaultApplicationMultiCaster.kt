package org.javen.framework.context.support

import org.javen.framework.context.InstanceObject
import org.javen.framework.context.beans.AbstractBeanFactory
import org.javen.framework.context.beans.BeanDefinition
import org.javen.framework.context.event.ApplicationEvent
import org.javen.framework.context.event.ApplicationEventMultiCaster
import org.javen.framework.context.event.ApplicationRefreshEventHandler
import org.javen.framework.context.event.EventListener
import org.javen.framework.core.annotation.Component
import org.javen.framework.core.annotation.Ignore
import org.javen.framework.core.type.reader.ClassMetadata
import org.javen.framework.core.type.reader.ClassReader
import org.javen.framework.core.type.scanner.ScanResult
import org.javen.framework.utils.UnsafeUtils
import java.util.Collections
import java.util.LinkedList
import java.util.Queue
import kotlin.reflect.KClass

@Ignore
class DefaultApplicationMultiCaster(
    scanningResult: ScanResult<Collection<KClass<*>>>,
    beanFactory: AbstractBeanFactory
) : ApplicationEventMultiCaster {

    private val eventHandlers: MutableList<EventListener<*>> = ArrayList()
    val unFinishInitHandlers: Queue<BeanDefinition> = LinkedList()

    init {
        val handlerTypes: Collection<KClass<out EventListener<*>>> = findEventListenerTypes(scanningResult)
        for (type: KClass<out EventListener<*>> in handlerTypes) {
            createHandler(type, beanFactory)
        }
        eventHandlers.add(ApplicationRefreshEventHandler(this, beanFactory, eventHandlers))
    }

    override fun <T> invokeHandlers(event: ApplicationEvent) {
        val handlers: List<EventListener<T>> = eventHandlers.stream()
            .filter { handler -> handler.listeningOn().isAssignableFrom(event::class.java) }
            .map { handler -> handler as EventListener<T> }
            .toList()
        for (handler in handlers) {
            handler.handle(handler.dataType?.cast(event.data))
        }
    }

    override fun selfCreate(vararg params: Any): InstanceObject {
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DefaultApplicationMultiCaster) return false

        if (eventHandlers != other.eventHandlers) return false
        if (unFinishInitHandlers != other.unFinishInitHandlers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = eventHandlers.hashCode()
        result = 31 * result + unFinishInitHandlers.hashCode()
        return result
    }

    private fun createDefinition(type: KClass<out EventListener<*>>, beanFactory: AbstractBeanFactory): BeanDefinition {
        val classReader: ClassReader = ClassReader.forType(type)
        val classMetadata: ClassMetadata = classReader.readClass()
        val javaType: Class<*> = type.java
        return BeanDefinition(
            classMetadata,
            beanFactory.resolveBeanName(javaType),
            javaType.getAnnotation(Component::class.java)
        )
    }

    private fun findEventListenerTypes(scanningResult: ScanResult<Collection<KClass<*>>>): Collection<KClass<out EventListener<*>>> {
        val classes: Collection<KClass<*>>? = scanningResult.get()
        return if (classes == null) {
            Collections.emptyList()
        } else {
            classes.stream()
                .filter { clazz -> EventListener::class.java.isAssignableFrom(clazz.java) }
                .map { clazz -> clazz as KClass<out EventListener<*>> }
                .toList()
        }
    }

    private fun createHandler(type: KClass<out EventListener<*>>, beanFactory: AbstractBeanFactory) {
        val definition = createDefinition(type, beanFactory)
        val requiredTypes: Array<Class<*>> = definition.getPrimaryConstructorParameters()
        if (requiredTypes.isEmpty()) {
            val eventHandler: EventListener<*> = createHandler(LinkedList(), type)
            eventHandlers.add(eventHandler)
        } else {
            unFinishInitHandlers.add(definition)
        }
    }

    fun createHandler(
        requiredObjects: MutableList<InstanceObject>,
        type: KClass<out EventListener<*>>
    ): EventListener<*> {
        val dummy: EventListener<*> = UnsafeUtils.allocateInstance(type.java)
        return if (requiredObjects.isEmpty()) {
            dummy.selfCreate() as EventListener<*>
        } else {
            dummy.selfCreate(requiredObjects.toTypedArray()) as EventListener<*>
        }
    }
}