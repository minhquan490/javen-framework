package org.javen.framework.io

import java.io.IOException
import java.io.InputStream
import kotlin.jvm.Throws

interface InputStreamResource {

    @Throws(IOException::class)
    fun getInputStream(): InputStream
}