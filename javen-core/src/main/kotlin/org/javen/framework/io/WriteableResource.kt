package org.javen.framework.io

import java.io.IOException
import java.nio.channels.WritableByteChannel
import kotlin.jvm.Throws

interface WriteableResource : Resource, OutputStreamResource {

    fun isWriteable(): Boolean

    @Throws(IOException::class)
    fun writableChannel(): WritableByteChannel
}