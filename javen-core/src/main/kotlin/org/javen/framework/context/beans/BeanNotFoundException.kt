package org.javen.framework.context.beans

class BeanNotFoundException(message: String, cause: Throwable?) : RuntimeException(message, cause) {

    constructor(message: String) : this(message, null)
}