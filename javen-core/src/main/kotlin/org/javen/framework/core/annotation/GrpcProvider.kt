package org.javen.framework.core.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class GrpcProvider(val name: String = "")
