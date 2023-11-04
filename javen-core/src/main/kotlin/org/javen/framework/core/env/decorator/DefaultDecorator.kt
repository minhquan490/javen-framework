package org.javen.framework.core.env.decorator

import org.javen.framework.core.env.Environment
import org.javen.framework.core.env.parser.Parser
import org.javen.framework.io.ResourceLoader
import java.net.URISyntaxException
import java.nio.file.Files
import java.util.Properties

class DefaultDecorator(private val parser: Parser) : PropertiesDecorator {

    override fun decorate(propertiesLine: String, properties: Properties) {
        try {
            val resourcePath: String = parser.parse(propertiesLine)
            val resourceLoader: ResourceLoader = parser.getResourceLoader()
                ?: throw IllegalArgumentException("No resource loader available")

            val resource = resourceLoader.getResource(resourcePath)
            val bufferReader = Files.newBufferedReader(resource.getResourcePath())

            var line: String?
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    if (line.startsWith(Environment.COMMENT_KEY)) {
                        continue
                    }
                    val keyPair: MutableList<String> = line.split(Environment.EQUALS_KEY).toMutableList()

                    if (keyPair[0] != Environment.INCLUDE_KEY && keyPair[1].contains(Environment.COMMA_KEY)) {
                        throw IllegalAccessException("Environment does not support multi value on key")
                    }

                    if (line.startsWith(Environment.INCLUDE_KEY)) {
                        decorate(keyPair[1] + Environment.CONFIG_FIG_SUFFIX, properties)
                        continue
                    }

                    keyPair[1] = parser.parse(keyPair[1])
                    properties.setProperty(keyPair[0], keyPair[1])
                }
            } while (line != null)

            bufferReader.close()
        } catch (e: URISyntaxException) {
            throw IllegalStateException("Can not config environment for line $propertiesLine", e)
        }
    }
}