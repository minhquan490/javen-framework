package org.javen.framework.context.beans

import org.javen.framework.context.InstanceObject

interface Bean<T : InstanceObject> {
    fun unwrap(): T

    fun isSingleton(): Boolean

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int
}