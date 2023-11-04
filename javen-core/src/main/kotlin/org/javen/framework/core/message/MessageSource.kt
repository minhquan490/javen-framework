package org.javen.framework.core.message

import java.util.Locale

interface MessageSource {
    fun getMessage(code: Int): String
    fun getMessage(code: Int, locale: Locale): String
    fun getMessage(code: Int, locale: Locale, params: Array<Any>?): String
}