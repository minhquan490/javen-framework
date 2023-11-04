package org.javen.framework.context.support

import org.javen.framework.context.InstanceObject
import org.javen.framework.context.event.ApplicationEvent

open class DefaultApplicationEvent : ApplicationEvent {
    companion object {
        private const val RESULT = true
    }

    override fun getData(): Any {
        return RESULT
    }

    override fun getDataType(): Class<*> {
        return Boolean::class.java
    }

    override fun selfCreate(vararg params: Any): InstanceObject {
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DefaultApplicationEvent) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}