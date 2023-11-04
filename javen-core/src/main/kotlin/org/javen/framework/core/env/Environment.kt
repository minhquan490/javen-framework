package org.javen.framework.core.env

import org.javen.framework.core.env.decorator.DefaultDecorator
import org.javen.framework.core.env.decorator.PropertiesDecorator
import org.javen.framework.core.env.parser.ClasspathParser
import org.javen.framework.core.env.parser.Parser
import org.javen.framework.io.FileSystemResourceLoader
import org.javen.framework.io.Resource
import org.javen.framework.io.ResourceLoader
import java.util.Properties
import java.util.concurrent.ConcurrentHashMap

class Environment private constructor(private var name: String) {

    private val properties: Properties

    init {
        if (name.contains("/")) {
            this.name = name.substring(name.lastIndexOf("/"))
        }
        val path: String = name.replace(name, "$CONFIG_FILE_PREFIX${name}$CONFIG_FIG_SUFFIX")
        this.properties = loadProperties(path)
    }

    companion object {
        const val CONFIG_FILE_PREFIX: String = "config/application-"
        const val CONFIG_FIG_SUFFIX: String = ".properties"
        const val CLASSPATH_KEY: String = "classpath:"
        const val INCLUDE_KEY: String = "include"
        const val CLASSPATH_CONFIG_KEY: String = "classpathConfig:"
        const val COMMENT_KEY = "#"
        const val EQUALS_KEY = "="
        const val COMMA_KEY = ","

        private val loadedEnvironments: MutableMap<String, EnvironmentHolder> = ConcurrentHashMap()

        fun getInstance(name: String?): Environment? {
            if (name == null) {
                return null
            }

            if (loadedEnvironments.contains(name)) {
                return loadedEnvironments[name]?.environment
            }

            if (name == "local" || name == "prod") {
                val environment = Environment(name)
                val holder = EnvironmentHolder(environment, true)
                loadedEnvironments[name] = holder
            }

            var loadedEnvironment = loadedEnvironments[name]
            if (loadedEnvironment == null) {
                val e = Environment(name)
                loadedEnvironment = EnvironmentHolder(e, false)
                loadedEnvironments[name] = loadedEnvironment
            }
            return loadedEnvironment.environment
        }

        fun getMainEnvironmentName(): String? {
            for (environment in loadedEnvironments.values) {
                if (environment.isMain) {
                    return environment.environment.name
                }
            }
            return null
        }
    }

    fun getEnvironmentName(): String {
        return this.name
    }

    fun getProperty(propertyName: String): String {
        return properties.getProperty(propertyName, propertyName)
    }


    private fun loadProperties(path: String): Properties {
        val resourceLoader: ResourceLoader = FileSystemResourceLoader()
        val resource: Resource = resourceLoader.getResource(path)
        val result = Properties()
        try {
            val decorator: PropertiesDecorator = createDecorator()

            decorator.decorate(resource.getResourcePath().toString(), result)
            return result
        } catch (e: Exception) {
            throw IllegalStateException("Can not load properties file [$path]", e)
        }
    }

    private fun createDecorator(): PropertiesDecorator {
        val parser: Parser = ClasspathParser(FileSystemResourceLoader())

        return DefaultDecorator(parser)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Environment

        if (name != other.name) return false
        if (properties != other.properties) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + properties.hashCode()
        return result
    }

    private data class EnvironmentHolder(val environment: Environment, var isMain: Boolean)
}