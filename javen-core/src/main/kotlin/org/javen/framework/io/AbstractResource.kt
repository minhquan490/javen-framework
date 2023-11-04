package org.javen.framework.io

import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile
import java.net.URI
import java.net.URL
import java.nio.channels.ReadableByteChannel
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.toPath

abstract class AbstractResource(resourcePath: Path, classLoader: ClassLoader) : ReadableResource {

    private val path: Path by lazy { initPath(resourcePath, classLoader) }

    @Throws(IOException::class)
    override fun getInputStream(): InputStream {
        return Files.newInputStream(this.path)
    }

    override fun isReadable(): Boolean {
        return Files.isReadable(getResourcePath())
    }

    @Throws(IOException::class)
    override fun readableChannel(): ReadableByteChannel {
        val randomAccessFile = RandomAccessFile(this.path.toFile(), ResourceMode.READ_ONLY.getMode())
        return randomAccessFile.channel
    }

    @Throws(IOException::class)
    override fun getResourceUrl(): URL {
        return getResourceUri().toURL()
    }

    @Throws(IOException::class)
    override fun getResourceUri(): URI {
        return this.path.toUri()
    }

    override fun isFile(): Boolean {
        return this.path.toFile().isFile
    }

    @Throws(IOException::class)
    override fun readDataAsByteArray(): ByteArray {
        return Files.readAllBytes(this.path)
    }

    @Throws(IOException::class)
    override fun readDataAsString(): String {
        return readDataAsString(StandardCharsets.UTF_8)
    }

    @Throws(IOException::class)
    override fun readDataAsString(charset: Charset): String {
        return String(readDataAsByteArray(), charset)
    }

    override fun getResourcePath(): Path {
        return this.path
    }

    protected abstract fun exist(path: Path): Boolean

    private fun initPath(path: Path, classLoader: ClassLoader): Path {
        return if (exist(path)) {
            path;
        } else {
            val url: URL? = classLoader.getResource(path.toString())
            url?.toURI()?.toPath() ?: throw FileNotFoundException("Path $path not found.")
        }
    }
}
