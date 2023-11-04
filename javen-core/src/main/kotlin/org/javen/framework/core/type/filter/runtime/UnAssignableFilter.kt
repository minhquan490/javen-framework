package org.javen.framework.core.type.filter.runtime

import org.javen.framework.core.type.filter.TypeFilter
import java.util.stream.Stream
import kotlin.reflect.KClass

class UnAssignableFilter(private val type: KClass<*>) : TypeFilter<KClass<*>> {
    override fun filter(stream: Stream<KClass<*>>): Stream<KClass<*>> {
        return stream.filter { clazz -> type.java.isAssignableFrom(clazz.java) }
    }
}