package org.javen.framework.context.support

import org.javen.framework.context.ActiveProfileProvider
import org.javen.framework.core.env.decorator.DefaultDecorator
import org.javen.framework.core.env.decorator.PropertiesDecorator
import org.javen.framework.core.env.parser.ClasspathParser
import org.javen.framework.io.FileSystemResourceLoader
import org.javen.framework.io.Resource
import org.javen.framework.io.ResourceLoader
import java.util.Properties

class ClasspathActiveProfileProvider : ActiveProfileProvider {

    companion object {
        private const val APPLICATION_FILE_NAME = "application.properties"
        private const val ACTIVE_PROFILE_PROPERTY = "active.profile"
    }

    private val fileSystemResourceLoader: ResourceLoader

    init {
        this.fileSystemResourceLoader = FileSystemResourceLoader()
    }

    override fun getActiveProfile(): String {
        val resource: Resource = fileSystemResourceLoader.getResource(APPLICATION_FILE_NAME)
        val parser = ClasspathParser(fileSystemResourceLoader)
        val decorator: PropertiesDecorator = DefaultDecorator(parser)
        val properties = Properties()

        try {
            decorator.decorate(resource.getResourcePath().toString(), properties)
        } catch (e: Exception) {
            throw IllegalStateException("Can not extract active.profile from file $APPLICATION_FILE_NAME")
        }

        return properties.getProperty(ACTIVE_PROFILE_PROPERTY)
    }
}