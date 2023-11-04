package org.javen.framework.core.annotation

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE, AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR)
annotation class Reachable(val fieldNames: Array<String> = [], val onClasses: Array<KClass<*>> = [])
