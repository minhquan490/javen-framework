@file:Suppress("UNCHECKED_CAST")

package org.javen.framework.core.type.scanner

import org.javen.framework.core.Ignore
import org.javen.framework.core.type.filter.TypeFilter
import org.javen.framework.core.type.filter.runtime.CanInstanceFilter
import org.javen.framework.core.type.filter.runtime.UnAnnotatedFiler
import org.javen.framework.io.FileSystemResourceLoader
import org.javen.framework.io.Resource
import org.javen.framework.io.ResourceLoader
import java.util.LinkedList
import java.util.stream.Stream

abstract class AbstractScanner<T>(private val classLoader: ClassLoader) : Scanner<Collection<T>> {
    private val fileSystemResourceLoader: ResourceLoader
    private val filters: MutableList<TypeFilter<*>> = LinkedList()

    init {
        this.fileSystemResourceLoader = FileSystemResourceLoader(classLoader)
        filters.add(UnAnnotatedFiler(Ignore::class))
        filters.add(CanInstanceFilter())
    }

    companion object {
        const val CLASS_EXTENSION: String = ".class"
    }

    override fun scan(paths: Collection<String>): ScanResult<Collection<T>> {
        val results: MutableSet<T> = HashSet()
        for (packageName in paths) {
            val relativePath: String = resolvePackageName(packageName)
            val resource: Resource = fileSystemResourceLoader.getResource(relativePath)
            val scannedResults: Collection<T> = doScanning(resource, packageName)
            val filteredResults: Stream<T> = applyFilters(scannedResults)
            results.addAll(filteredResults.toList())
        }
        return ScanResult(results)
    }

    final override fun getClassLoader(): ClassLoader {
        return this.classLoader
    }

    final override fun <U> addFilter(filter: TypeFilter<U>) {
        filters.add(filter)
    }

    protected abstract fun doScanning(resource: Resource, packageName: String): Collection<T>

    private fun applyFilters(target: Collection<T>): Stream<T> {
        var result: Stream<T>? = target.stream()
        for (filter in filters) {
            val castedFilter: TypeFilter<T> = filter as TypeFilter<T>
            result = result?.let { castedFilter.filter(it) }
        }
        return result ?: Stream.empty()
    }

    private fun resolvePackageName(packageName: String): String {
        return if (packageName.contains(".")) {
            packageName.replace(".", "/")
        } else {
            packageName
        }
    }
}