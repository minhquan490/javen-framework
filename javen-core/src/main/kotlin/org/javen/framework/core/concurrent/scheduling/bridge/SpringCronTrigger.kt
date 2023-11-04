package org.javen.framework.core.concurrent.scheduling.bridge

import org.javen.framework.core.concurrent.scheduling.Trigger
import org.javen.framework.core.concurrent.scheduling.TriggerContext
import org.springframework.scheduling.support.CronTrigger
import java.time.Instant

class SpringCronTrigger(private val expression: String) : AbstractSpringTriggerWrapper(), Trigger {

    override fun nextExecution(triggerContext: TriggerContext): Instant? {
        return if (triggerContext is SpringTriggerContextHolder) {
            springTrigger.nextExecution(triggerContext.unwrap())
        } else {
            null
        }
    }

    override fun createTrigger(): org.springframework.scheduling.Trigger {
        return CronTrigger(expression)
    }

}