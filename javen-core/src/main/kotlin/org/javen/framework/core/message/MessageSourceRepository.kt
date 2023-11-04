package org.javen.framework.core.message

import org.javen.framework.context.InstanceObject
import java.util.Locale

interface MessageSourceRepository : InstanceObject {
    fun getMessage(id: Int, locale: Locale): String
}