package org.javen.framework.core.type.scanner

import org.javen.framework.io.FileSystemResourceLoader
import org.javen.framework.io.Resource
import org.javen.framework.io.ResourceLoader
import java.io.File
import java.nio.file.Path
import java.util.Collections
import java.util.Enumeration
import java.util.LinkedList
import java.util.Queue
import java.util.jar.JarEntry
import java.util.jar.JarFile
import kotlin.reflect.KClass

open class PackageScanner(classLoader: ClassLoader) : AbstractScanner<KClass<*>>(classLoader) {

    private val fileSystemResourceLoader: ResourceLoader

    init {
        this.fileSystemResourceLoader = FileSystemResourceLoader(classLoader)
    }

    override fun doScanning(resource: Resource, packageName: String): Collection<KClass<*>> {
        return if (resource.isFile()) {
            readClassesFromJar(resource.getResourcePath())
        } else {
            readClassesFromDirectory(resource.getResourcePath(), packageName)
        }
    }

    private fun readClassesFromJar(fullPath: Path): Collection<KClass<*>> {
        val results: MutableSet<KClass<*>> = HashSet()
        val dotReplacementRegex = Regex("/|\\\\\\\\")
        val jarPath: String = fullPath.toString().replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "")
        val jarFile = JarFile(jarPath)
        val entries: Enumeration<JarEntry> = jarFile.entries()
        while (entries.hasMoreElements()) {
            val entry: JarEntry = entries.nextElement()
            val entryName: String = entry.name
            if (entryName.endsWith(CLASS_EXTENSION)) {
                val className: String = entryName.replace(dotReplacementRegex, ".").replace(CLASS_EXTENSION, "")
                val loadedClass: KClass<*>? = loadKotlinClass(className)
                if (loadedClass != null) {
                    results.add(loadedClass)
                }
            }
        }
        return results
    }

    private fun readClassesFromDirectory(directoryPath: Path, packageName: String): Collection<KClass<*>> {
        val results: MutableSet<KClass<*>> = HashSet()
        val directory: File = directoryPath.toFile()
        val files: Queue<File> = LinkedList(directory.listFiles()?.asList() ?: Collections.emptyList())
        while (files.isNotEmpty()) {
            val classFile: File = files.poll()
            if (classFile.isDirectory) {
                val scannedDirectoryResults: Collection<KClass<*>> = readClassesFromDirectory(
                    classFile.toPath(),
                    packageName.plus(".${classFile.name}")
                ) // n^n time complexity
                results.addAll(scannedDirectoryResults)
            } else if (classFile.name.endsWith(CLASS_EXTENSION)) {
                val className = "${packageName}.${resolveClassSimpleName(classFile)}"
                val loadedClass: KClass<*>? = loadKotlinClass(className)
                if (loadedClass != null) {
                    results.add(loadedClass)
                }
            }
        }
        return results
    }

    private fun resolveClassSimpleName(classFile: File): String {
        return classFile.name.replace(CLASS_EXTENSION, "")
    }

    private fun loadKotlinClass(className: String): KClass<*>? {
        return try {
            val javaClass: Class<*> = Class.forName(className)
            javaClass.kotlin
        } catch (e: ClassNotFoundException) {
            null
        }
    }
}