package org.javen.framework.core.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Component(val name: String = "", val lazy: Boolean = false, val scope: Scope = Scope.SINGLETON) {

    enum class Scope {
        SINGLETON,
        PROTOTYPE
    }
}
