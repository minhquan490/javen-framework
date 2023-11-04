package org.javen.framework.core.annotation

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ApplyOn(val entity: KClass<*> = Void::class, val order: Int = Int.MAX_VALUE)
