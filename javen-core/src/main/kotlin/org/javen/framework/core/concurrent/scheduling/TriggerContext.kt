package org.javen.framework.core.concurrent.scheduling

import java.time.Instant

interface TriggerContext {
    fun lastScheduledExecution(): Instant?
    fun lastActualExecution(): Instant?
    fun lastCompletion(): Instant?
}