package org.javen.framework.context.event

interface EventPublisher {
    fun fireEvent(event: ApplicationEvent)
}