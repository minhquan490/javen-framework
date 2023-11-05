package org.javen.framework.logger

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import ch.qos.logback.classic.spi.Configurator
import ch.qos.logback.core.spi.ContextAwareBase
import org.javen.framework.core.env.parser.ClasspathParser
import org.javen.framework.io.FileSystemResourceLoader
import org.javen.framework.io.Resource
import org.javen.framework.io.utils.ResourceUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path

class ApplicationLoggingConfigurator : ContextAwareBase(), Configurator {

    companion object {
        private const val GET_LOGGER_METHOD_NAME: String = "getLogger"
    }

    override fun configure(context: LoggerContext?): Configurator.ExecutionStatus {
        val fileSystemLoader = FileSystemResourceLoader()
        val resource: Resource = fileSystemLoader.getResource(ResourceUtils.getApplicationPropertiesFileName())
        val parser = ClasspathParser(fileSystemLoader)
        val properties = ResourceUtils.loadPropertiesFile(resource, parser)

        val loggingFileConfigPath: String =
            parser.parse(properties.getProperty(ResourceUtils.getLoggingConfigFilePropertyKey()))
        doConfig(Path.of(loggingFileConfigPath))
        LogFactoryBinder.bind(LoggerFactory::class.java, GET_LOGGER_METHOD_NAME, Logger::class.java)
        return Configurator.ExecutionStatus.INVOKE_NEXT_IF_ANY
    }

    private fun doConfig(filePath: Path) {
        validatePath(filePath)
        val configurator = JoranConfigurator()
        configurator.context = this.context
        configurator.doConfigure(filePath.toFile())
    }

    private fun validatePath(filePath: Path) {
        if (!Files.exists(filePath)) {
            throw FileNotFoundException("Logging config file with path [${filePath}] not found")
        }
        require(filePath.endsWith(".xml")) { "Logging config file is a xml file" }
    }
}