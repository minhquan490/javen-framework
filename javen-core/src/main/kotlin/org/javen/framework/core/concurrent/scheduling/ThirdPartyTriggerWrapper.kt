package org.javen.framework.core.concurrent.scheduling

interface ThirdPartyTriggerWrapper<T> {
    fun unwrap(): T
}