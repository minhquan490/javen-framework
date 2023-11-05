package org.javen.framework.core

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Scope(val value: ControllerScope = ControllerScope.SINGLETON) {
    enum class ControllerScope {
        SINGLETON,
        REQUEST
    }
}
