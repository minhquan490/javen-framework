package org.javen.framework.core.concurrent.scheduling

import java.time.Instant

interface Trigger {
    fun nextExecution(triggerContext: TriggerContext): Instant?
}