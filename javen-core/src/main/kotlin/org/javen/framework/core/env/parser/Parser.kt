package org.javen.framework.core.env.parser

import org.javen.framework.io.ResourceLoader

interface Parser {
    fun parse(target: String): String

    fun getResourceLoader(): ResourceLoader?
}