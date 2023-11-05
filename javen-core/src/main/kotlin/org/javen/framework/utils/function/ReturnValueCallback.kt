package org.javen.framework.utils.function

interface ReturnValueCallback {
    fun call(vararg params: Any): Any
}