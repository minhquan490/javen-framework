package org.javen.framework.context

import kotlin.reflect.KClass

interface ObjectFactory {
    fun getInstance(type: KClass<out InstanceObject>, vararg params: Any): InstanceObject
}