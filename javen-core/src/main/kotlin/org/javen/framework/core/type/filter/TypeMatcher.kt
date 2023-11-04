package org.javen.framework.core.type.filter

import kotlin.reflect.KClass

interface TypeMatcher {
    fun match(target: KClass<*>): Boolean
}