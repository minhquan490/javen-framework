package org.javen.framework.context.support

import org.javen.framework.context.ActiveProfileProvider
import org.javen.framework.core.env.parser.ClasspathParser
import org.javen.framework.io.FileSystemResourceLoader
import org.javen.framework.io.Resource
import org.javen.framework.io.ResourceLoader
import org.javen.framework.io.utils.ResourceUtils

class ClasspathActiveProfileProvider : ActiveProfileProvider {

    private val fileSystemResourceLoader: ResourceLoader

    init {
        this.fileSystemResourceLoader = FileSystemResourceLoader()
    }

    override fun getActiveProfile(): String {
        val resource: Resource = fileSystemResourceLoader.getResource(ResourceUtils.getApplicationPropertiesFileName())
        val parser = ClasspathParser(fileSystemResourceLoader)
        val properties = ResourceUtils.loadPropertiesFile(resource, parser)

        return properties.getProperty(ResourceUtils.getActiveProfilePropertyKey())
    }
}