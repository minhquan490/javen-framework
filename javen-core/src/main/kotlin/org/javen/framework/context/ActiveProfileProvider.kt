package org.javen.framework.context

interface ActiveProfileProvider {
    fun getActiveProfile(): String
}