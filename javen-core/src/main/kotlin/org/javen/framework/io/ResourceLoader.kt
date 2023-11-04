package org.javen.framework.io

interface ResourceLoader {

    fun getResource(path: String): Resource

    fun getClassLoader(): ClassLoader?
}