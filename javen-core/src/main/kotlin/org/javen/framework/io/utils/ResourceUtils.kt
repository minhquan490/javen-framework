package org.javen.framework.io.utils

import org.javen.framework.core.env.decorator.DefaultDecorator
import org.javen.framework.core.env.decorator.PropertiesDecorator
import org.javen.framework.core.env.parser.Parser
import org.javen.framework.io.Resource
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.util.Properties

class ResourceUtils private constructor() {
    companion object {

        fun getApplicationPropertiesFileName(): String {
            return "application.properties"
        }

        fun getActiveProfilePropertyKey(): String {
            return "active.profile"
        }

        fun getLoggingConfigFilePropertyKey(): String {
            return "logging.config"
        }

        fun loadPropertiesFile(resource: Resource, parser: Parser): Properties {
            val filePath: Path = resource.getResourcePath()
            if (Files.exists(filePath)) {
                val decorator: PropertiesDecorator = DefaultDecorator(parser)
                val properties = Properties()

                try {
                    decorator.decorate(resource.getResourcePath().toString(), properties)
                } catch (e: Exception) {
                    throw IllegalStateException("Can not extract active.profile from file $ResourceUtils.getApplicationPropertiesFileName()")
                }
                return properties
            }
            throw FileNotFoundException("Resource with path [${filePath}}] not found.")
        }
    }
}