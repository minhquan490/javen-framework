package org.javen.framework.core.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Scope(val value: ControllerScope = ControllerScope.SINGLETON) {
    enum class ControllerScope {
        SINGLETON,
        REQUEST
    }
}
