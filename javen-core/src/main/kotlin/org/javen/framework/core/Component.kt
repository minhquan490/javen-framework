package org.javen.framework.core

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Component(
    val name: String = "",
    val lazy: Boolean = false,
    val order: Int = Ordered.LOWEST
)
