package org.javen.framework.core.type.filter

import java.util.stream.Stream

interface TypeFilter<T> {
    fun filter(stream: Stream<T>): Stream<T>
}