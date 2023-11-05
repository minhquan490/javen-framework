package org.javen.framework.core.type.scanner

import org.javen.framework.context.InstanceObject
import org.javen.framework.context.event.EventListener
import org.javen.framework.core.Component
import org.javen.framework.core.type.filter.runtime.AnnotatedFilter
import org.javen.framework.core.type.filter.runtime.AssignableFilter
import org.javen.framework.core.type.filter.runtime.UnAssignableFilter

class StereotypeScanner : CachingApplicationScanner() {

    init {
        addFilter(AnnotatedFilter(Component::class))
        addFilter(AssignableFilter(InstanceObject::class))
        addFilter(UnAssignableFilter(EventListener::class))
    }
}