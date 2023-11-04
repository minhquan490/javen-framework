package org.javen.framework.core.type.filter.runtime

import org.javen.framework.core.type.filter.TypeFilter
import java.lang.reflect.Modifier
import java.util.stream.Stream
import kotlin.reflect.KClass

class CanInstanceFilter : TypeFilter<KClass<*>> {
    override fun filter(stream: Stream<KClass<*>>): Stream<KClass<*>> {
        return stream.filter { clazz -> !clazz.isAbstract }
            .filter { clazz -> !Modifier.isInterface(clazz.java.modifiers) }
            .filter { clazz -> clazz.java.isAnnotation }
    }
}