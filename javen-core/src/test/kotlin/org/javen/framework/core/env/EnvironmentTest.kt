package org.javen.framework.core.env

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EnvironmentTest {

    @Test
    fun getInstance_DoesNotNull() {
        val environment: Environment? = Environment.getInstance("local")
        Assertions.assertNotNull(environment)
    }

    @Test
    fun getInstance_ThrowIllegalStateException() {
        Assertions.assertThrows(IllegalStateException::class.java) { Environment.getInstance("prod") }
    }

    @Test
    fun getMainEnvironmentName_EqualsLocal() {
        Environment.getInstance("local")
        Assertions.assertEquals("local", Environment.getMainEnvironmentName())
    }

    @Test
    fun getMainEnvironmentName_IsNull() {
        Environment.getInstance("common")
        Assertions.assertNull(Environment.getMainEnvironmentName())
    }

    @Test
    fun getProperty_NormalCase() {
        val environment: Environment? = Environment.getInstance("local")
        Assertions.assertEquals("Test", environment?.getProperty("server.application.name"))
    }

    @Test
    fun getProperty_CanInclude() {
        val environment: Environment? = Environment.getInstance("local")
        Assertions.assertEquals("Success", environment?.getProperty("server.include"))
    }
}