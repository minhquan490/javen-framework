package org.javen.framework.core.annotation

/**
 * Use for active grpc code generation feature for support custom grpc.
 *
 * @note Only use in main class.
 * @author Hoang Minh Quan.
 * */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ActiveGrpc()
