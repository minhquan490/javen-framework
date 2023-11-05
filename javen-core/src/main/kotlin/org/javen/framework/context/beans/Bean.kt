package org.javen.framework.context.beans

import org.javen.framework.context.InstanceObject
import org.javen.framework.core.Ordered

interface Bean<T : InstanceObject> : Ordered {
    fun unwrap(): T

    fun isSingleton(): Boolean

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int
}