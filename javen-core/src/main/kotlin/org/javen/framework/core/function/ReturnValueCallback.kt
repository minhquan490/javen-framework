package org.javen.framework.core.function

interface ReturnValueCallback {
    fun call(vararg params: Any): Any
}