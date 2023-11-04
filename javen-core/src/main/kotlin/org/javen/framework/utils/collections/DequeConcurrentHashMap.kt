package org.javen.framework.utils.collections

import java.util.concurrent.ConcurrentHashMap

class DequeConcurrentHashMap<K : Any, V : Any> : ConcurrentHashMap<K, V>() {

    @Transient
    private var firstKey: K? = null

    @Transient
    private var lastKey: K? = null

    override fun put(key: K, value: V): V? {
        if (super.isEmpty()) {
            firstKey = key
        }
        lastKey = key
        return super.put(key, value)
    }

    fun getFirst(): V? {
        return firstKey?.let { super.get(it) }
    }

    fun getLast(): V? {
        return lastKey?.let { super.get(it) }
    }
}