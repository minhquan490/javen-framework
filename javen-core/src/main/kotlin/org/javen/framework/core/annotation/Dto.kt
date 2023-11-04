package org.javen.framework.core.annotation

/**
 * Specify a target is a data transfer object (dto) of another class.
 *
 * @param forType Full qualifier type name of type for dto apply on.
 * */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Dto(val forType: String)
