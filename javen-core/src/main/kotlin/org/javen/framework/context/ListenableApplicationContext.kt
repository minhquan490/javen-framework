package org.javen.framework.context

import org.javen.framework.core.message.MessageSource
import java.io.Closeable

interface ListenableApplicationContext : ApplicationContext, MessageSource, Closeable {
    fun start()
    fun getTotalStartTimeInMillisecond(): Long
}