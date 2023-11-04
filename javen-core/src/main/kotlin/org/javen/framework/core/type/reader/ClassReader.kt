package org.javen.framework.core.type.reader

import kotlin.reflect.KClass

class ClassReader private constructor(private val type: KClass<*>) {
    companion object {
        fun forType(type: KClass<*>): ClassReader {
            return ClassReader(type)
        }
    }

    fun readClass(): ClassMetadata {
        return ClassMetadata(type.java)
    }
}