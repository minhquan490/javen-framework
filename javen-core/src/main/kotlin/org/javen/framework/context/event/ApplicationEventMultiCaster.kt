package org.javen.framework.context.event

import org.javen.framework.context.InstanceObject

interface ApplicationEventMultiCaster : InstanceObject {
    fun <T> invokeHandlers(event: ApplicationEvent)
}