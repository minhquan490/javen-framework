package org.javen.framework.core.type.filter.runtime

import org.javen.framework.core.type.filter.TypeFilter
import java.util.stream.Stream
import kotlin.reflect.KClass

class AssignableFilter(private val checker: KClass<*>) : TypeFilter<KClass<*>> {
    override fun filter(stream: Stream<KClass<*>>): Stream<KClass<*>> {
        return stream.filter { t -> checker.java.isAssignableFrom(t.java) }
    }
}