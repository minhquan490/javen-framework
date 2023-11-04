package org.javen.framework.io

import java.io.IOException
import java.nio.channels.ReadableByteChannel
import kotlin.jvm.Throws

interface ReadableResource : Resource {
    fun isReadable(): Boolean

    @Throws(IOException::class)
    fun readableChannel(): ReadableByteChannel
}