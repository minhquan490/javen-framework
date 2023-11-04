package org.javen.framework.core.type.filter.runtime

import org.javen.framework.core.type.filter.TypeFilter
import java.util.stream.Stream
import kotlin.reflect.KClass

class AnnotatedFilter(private val annotation: KClass<out Annotation>) : TypeFilter<KClass<*>> {

    override fun filter(stream: Stream<KClass<*>>): Stream<KClass<*>> {
        return stream.filter { clazz -> filterSuperClass(clazz.java, annotation.java) }
    }

    private fun filterSuperClass(type: Class<*>?, annotationType: Class<out Annotation>): Boolean {
        if (type == null) {
            return false
        }
        return if (type.isAnnotationPresent(annotationType)) {
            true
        } else {
            filterSuperClass(type.superclass, annotationType)
        }
    }
}