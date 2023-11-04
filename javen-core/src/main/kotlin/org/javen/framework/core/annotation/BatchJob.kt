package org.javen.framework.core.annotation

/**
 * Mark a class is a batch job.
 *
 * @param name Name of that batch job.
 * */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class BatchJob(val name: String)
