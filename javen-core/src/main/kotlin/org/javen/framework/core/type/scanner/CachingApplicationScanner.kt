package org.javen.framework.core.type.scanner

import org.javen.framework.core.type.filter.TypeFilter
import java.util.LinkedList
import kotlin.reflect.KClass

open class CachingApplicationScanner : Scanner<Collection<KClass<*>>> {

    private val packageScanner: PackageScanner = PackageScanner(ClassLoader.getSystemClassLoader())

    companion object {
        private val CACHED_SCANNING_RESULT: MutableList<KClass<*>> = LinkedList()
    }

    override fun scan(paths: Collection<String>): ScanResult<Collection<KClass<*>>> {
        if (CACHED_SCANNING_RESULT.isEmpty()) {
            val result: ScanResult<Collection<KClass<*>>> = packageScanner.scan(paths)
            result.get()?.let { CACHED_SCANNING_RESULT.addAll(it) }
        }
        return ScanResult(CACHED_SCANNING_RESULT)
    }

    override fun getClassLoader(): ClassLoader {
        return packageScanner.getClassLoader()
    }

    override fun <U> addFilter(filter: TypeFilter<U>) {
        packageScanner.addFilter(filter)
    }

    fun release() {
        CACHED_SCANNING_RESULT.clear()
    }
}