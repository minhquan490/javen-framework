package org.javen.framework.io

import org.javen.framework.utils.NativeLibraryUtils
import java.nio.file.Path

class NativeLibraryLoader(private val loader: ClassLoader) : ResourceLoader {

    constructor() : this(ClassLoader.getSystemClassLoader())

    override fun getResource(path: String): Resource {
        return NativeLibraryResource(Path.of(path), getClassLoader())
    }

    override fun getClassLoader(): ClassLoader {
        return this.loader
    }

    fun loadLib(libName: String, classLoader: ClassLoader) {
        try {
            NativeLibraryUtils.loadLibrary(libName, false)
        } catch (e: UnsatisfiedLinkError) {
            val nativeLibraryLoader = NativeLibraryLoader(classLoader)
            val nativeLibraryResource = nativeLibraryLoader.getResource(libName)
            NativeLibraryUtils.loadLibrary(nativeLibraryResource.getResourcePath().toString(), true)
        }
    }
}