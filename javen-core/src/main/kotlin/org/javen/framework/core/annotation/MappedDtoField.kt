package org.javen.framework.core.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class MappedDtoField(val targetField: String, val outputJsonField: String)
