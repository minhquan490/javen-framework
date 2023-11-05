package org.javen.framework.context

import org.javen.framework.core.message.MessageNotFoundException
import org.javen.framework.core.message.MessageSource
import java.util.Locale

abstract class AbstractMessageSourceApplicationContext(basePackages: Collection<String>) :
    AbstractListenableApplicationContext(basePackages), MessageSource {
    override fun getMessage(code: Int): String {
        return getMessage(code, Locale.getDefault())
    }

    override fun getMessage(code: Int, locale: Locale): String {
        return getMessage(code, locale, null)
    }

    override fun getMessage(code: Int, locale: Locale, params: Array<Any>?): String {
        val template: String = doGetMessageTemplate(code, locale)
            ?: throw MessageNotFoundException("Message with code [${code}] not found")
        return if (params != null) {
            formatMessage(template, params)
        } else {
            template
        }
    }

    protected abstract fun doGetMessageTemplate(code: Int, locale: Locale): String?
    protected abstract fun formatMessage(template: String, params: Array<Any>): String
}