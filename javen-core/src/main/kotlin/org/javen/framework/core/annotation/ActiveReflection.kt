package org.javen.framework.core.annotation

/**
 * Specify the target will accessible by reflection when run in native application.
 *
 * @author Hoang Minh Quan.
 * */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION)
annotation class ActiveReflection()