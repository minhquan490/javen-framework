@file:Suppress("UNCHECKED_CAST")

package org.javen.framework.context.event

import org.javen.framework.context.InstanceObject
import org.javen.framework.context.beans.AbstractBeanFactory
import org.javen.framework.context.beans.BeanDefinition
import org.javen.framework.context.support.DefaultApplicationMultiCaster
import org.javen.framework.core.Component
import org.javen.framework.core.Ignore
import java.util.LinkedList
import java.util.Queue
import kotlin.reflect.KClass

@Component
@Ignore // Does not allow scanner scans this handler
class ApplicationRefreshEventHandler(
    private val target: DefaultApplicationMultiCaster,
    private val beanFactory: AbstractBeanFactory,
    private val eventHandlers: MutableList<EventListener<*>>
) : EventListener<Boolean> {
    override fun listeningOn(): Class<out ApplicationEvent> {
        return ApplicationRefreshEvent::class.java
    }

    override fun handle(eventData: Boolean?) {
        val unFinishHandlers: Queue<BeanDefinition> = target.unFinishInitHandlers
        while (unFinishHandlers.isNotEmpty()) {
            val definition: BeanDefinition = unFinishHandlers.poll()
            val requiredTypes = definition.getPrimaryConstructorParameters()
            val requiredObjects: MutableList<InstanceObject> = LinkedList()
            for (requiredType in requiredTypes) {
                val beanName: String = beanFactory.resolveBeanName(requiredType)
                requiredObjects.add(beanFactory.getBean(beanName))
            }
            val createdHandler: EventListener<*> =
                target.createHandler(requiredObjects, definition.getType().kotlin as KClass<out EventListener<*>>)
            eventHandlers.add(createdHandler)
        }
    }

    override fun selfCreate(vararg params: Any): InstanceObject {
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ApplicationRefreshEventHandler) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}