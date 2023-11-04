package org.javen.framework.core.concurrent.scheduling.bridge

import org.javen.framework.core.concurrent.scheduling.ThirdPartyTriggerWrapper
import org.springframework.scheduling.Trigger

abstract class AbstractSpringTriggerWrapper : ThirdPartyTriggerWrapper<Trigger> {
    protected val springTrigger: Trigger by lazy { createTrigger() }

    override fun unwrap(): Trigger {
        return springTrigger
    }

    protected abstract fun createTrigger(): Trigger
}