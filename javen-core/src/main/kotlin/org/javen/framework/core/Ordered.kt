package org.javen.framework.core

interface Ordered {

    companion object {
        const val HIGHEST: Int = Int.MIN_VALUE
        const val LOWEST: Int = Int.MAX_VALUE
    }

    fun getOrder(): Int
}