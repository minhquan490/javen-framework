package org.javen.framework.io

import java.io.IOException
import java.net.URI
import java.net.URL
import java.nio.charset.Charset
import java.nio.file.Path
import kotlin.jvm.Throws

interface Resource : InputStreamResource {

    @Throws(IOException::class)
    fun getResourceUrl(): URL

    @Throws(IOException::class)
    fun getResourceUri(): URI

    fun getResourcePath(): Path

    fun isFile(): Boolean

    @Throws(IOException::class)
    fun readDataAsByteArray(): ByteArray

    @Throws(IOException::class)
    fun readDataAsString(): String

    @Throws(IOException::class)
    fun readDataAsString(charset: Charset): String
}