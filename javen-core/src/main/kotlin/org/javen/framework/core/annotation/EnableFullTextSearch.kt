package org.javen.framework.core.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class EnableFullTextSearch(val proxyPackage: String = "")
