package org.javen.framework.utils

class NativeLibraryUtils private constructor() {
    companion object {
        fun loadLibrary(libName: String, absolute: Boolean) {
            if (absolute) {
                System.load(libName)
            } else {
                System.loadLibrary(libName)
            }
        }
    }
}