package org.javen.framework.context

import org.javen.framework.context.beans.BeanFactory
import org.javen.framework.context.beans.BeanFactoryCapable
import org.javen.framework.context.event.EventPublisher
import org.javen.framework.core.env.EnvironmentAware
import org.javen.framework.core.env.EnvironmentCapable

interface ApplicationContext : EnvironmentCapable, EnvironmentAware, BeanFactory, BeanFactoryCapable, EventPublisher {
    fun getId(): String
}