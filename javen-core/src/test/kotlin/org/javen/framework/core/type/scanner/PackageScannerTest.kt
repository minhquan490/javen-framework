package org.javen.framework.core.type.scanner

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.FileNotFoundException

class PackageScannerTest {

    @Test
    fun scan_doesNotThrow() {
        val packages: List<String> = listOf("org.javen.framework")
        val scanner = PackageScanner(ClassLoader.getSystemClassLoader())
        val result = scanner.scan(packages)
        val collection = result.get()
        if (collection != null) {
            Assertions.assertEquals(1, collection.size)
        }
    }

    @Test
    fun scan_mustBeThrowFileNotFound() {
        val packages: List<String> = listOf("org.jetbrains.kotlin")
        val scanner = PackageScanner(ClassLoader.getSystemClassLoader())
        Assertions.assertThrows(FileNotFoundException::class.java) { scanner.scan(packages) }
    }
}