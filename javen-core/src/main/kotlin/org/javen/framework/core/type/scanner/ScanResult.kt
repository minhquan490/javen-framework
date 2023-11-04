package org.javen.framework.core.type.scanner

data class ScanResult<T>(private val value: T?) {
    fun isReady(): Boolean {
        return value != null
    }

    fun get(): T? {
        return value
    }
}
