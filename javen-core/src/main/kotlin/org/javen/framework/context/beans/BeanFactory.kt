package org.javen.framework.context.beans

import org.javen.framework.context.InstanceObject
import kotlin.reflect.KClass

interface BeanFactory {
    fun getBean(name: String): InstanceObject

    fun <T : InstanceObject> getBean(type: KClass<T>): T

    fun <T : InstanceObject> getBean(name: String, type: KClass<T>): T
}