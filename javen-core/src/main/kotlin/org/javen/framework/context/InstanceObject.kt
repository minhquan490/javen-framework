package org.javen.framework.context

interface InstanceObject {
    fun selfCreate(vararg params: Any): InstanceObject

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int
}