package org.javen.framework.io

enum class ResourceMode(private val mode: String) {
    READ_ONLY("r"),
    READ_WRITE("rw");

    fun getMode(): String {
        return this.mode
    }
}