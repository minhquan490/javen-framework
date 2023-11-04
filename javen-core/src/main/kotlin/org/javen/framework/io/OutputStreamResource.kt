package org.javen.framework.io

import java.io.IOException
import java.io.OutputStream
import kotlin.jvm.Throws

interface OutputStreamResource {

    @Throws(IOException::class)
    fun getOutputStream(): OutputStream
}