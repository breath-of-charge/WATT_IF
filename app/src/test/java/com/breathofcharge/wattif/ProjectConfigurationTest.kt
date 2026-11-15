package com.breathofcharge.wattif

import org.junit.Assert.assertEquals
import org.junit.Test

class ProjectConfigurationTest {
    @Test
    fun initialProjectConfigurationIsActive() {
        assertEquals(
            "com.breathofcharge.wattif",
            ProjectConfigurationTest::class.java.packageName,
        )
    }
}
