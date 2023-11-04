package org.javen.framework.core.type.scanner

import org.javen.framework.core.type.filter.TypeFilter

interface Scanner<T> {
    fun scan(paths: Collection<String>): ScanResult<T>

    fun getClassLoader(): ClassLoader

    fun <U> addFilter(filter: TypeFilter<U>)
}