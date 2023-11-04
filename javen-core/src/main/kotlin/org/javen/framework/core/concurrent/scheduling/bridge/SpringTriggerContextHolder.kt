package org.javen.framework.core.concurrent.scheduling.bridge

import org.javen.framework.core.concurrent.scheduling.TriggerContext
import org.springframework.scheduling.support.SimpleTriggerContext
import java.time.Clock
import java.time.Instant

class SpringTriggerContextHolder : TriggerContext {

    private val springTriggerContext: org.springframework.scheduling.TriggerContext

    init {
        springTriggerContext = SimpleTriggerContext(Clock.systemDefaultZone())
    }

    override fun lastScheduledExecution(): Instant? {
        return springTriggerContext.lastScheduledExecution()
    }

    override fun lastActualExecution(): Instant? {
        return springTriggerContext.lastActualExecution()
    }

    override fun lastCompletion(): Instant? {
        return springTriggerContext.lastCompletion()
    }

    fun unwrap(): org.springframework.scheduling.TriggerContext {
        return springTriggerContext
    }
}