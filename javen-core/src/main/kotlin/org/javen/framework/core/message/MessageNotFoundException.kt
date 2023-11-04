package org.javen.framework.core.message

class MessageNotFoundException(override val message: String?, override val cause: Throwable?) : RuntimeException() {
    constructor(message: String?) : this(message, null)
}