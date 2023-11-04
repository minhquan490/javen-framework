package org.javen.framework.core.env.parser

import org.javen.framework.core.env.Environment
import org.javen.framework.io.ResourceLoader
import java.net.URL
import java.text.MessageFormat

class ClasspathParser(private val loader: ResourceLoader) : Parser {
    override fun parse(target: String): String {
        return if (target.startsWith(Environment.CLASSPATH_KEY)) {
            val u: URL = loader.getResource(target).getResourceUrl()
            resolveResult(u.path)
        } else if (target.startsWith(Environment.CLASSPATH_CONFIG_KEY)) {
            var actualPropName: String =
                target.replace(Environment.CLASSPATH_CONFIG_KEY, Environment.CONFIG_FILE_PREFIX)
            if (!actualPropName.endsWith(Environment.CONFIG_FIG_SUFFIX)) {
                actualPropName += Environment.CONFIG_FIG_SUFFIX
            }
            val template: String = Environment.CLASSPATH_KEY + "{0}"
            val u: URL = loader.getResource(MessageFormat.format(template, actualPropName)).getResourceUrl()
            resolveResult(u.path)
        } else {
            target
        }
    }

    override fun getResourceLoader(): ResourceLoader {
        return this.loader
    }

    private fun resolveResult(target: String): String {
        return if (target.contains(":")) {
            target.substring(target.indexOf(":") + 1)
        } else {
            target
        }
    }
}