package org.javen.framework.io

class FileSystemResourceLoader(private val classLoader: ClassLoader) : ResourceLoader {

    constructor() : this(ClassLoader.getSystemClassLoader())

    override fun getResource(path: String): Resource {
        return FileSystemResource(path, getClassLoader())
    }

    override fun getClassLoader(): ClassLoader {
        return this.classLoader
    }
}