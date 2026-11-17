package com.breathofcharge.wattif.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class ArchitectureKonsistTest {
    private val basePackage = "com.breathofcharge.wattif"
    private val productionScope = Konsist.scopeFromProduction()

    @Test
    fun `layer dependencies follow the project architecture`() {
        productionScope.files
            .filterNot { it.name == "MainActivity" }
            .assertArchitecture {
                val ui = Layer("UI", "com.breathofcharge.wattif.ui..")
                val domain = Layer("Domain", "com.breathofcharge.wattif.domain..")
                val data = Layer("Data", "com.breathofcharge.wattif.data..")

                ui.dependsOn(domain)
                domain.dependsOnNothing()
                data.dependsOn(domain)
            }
    }

    @Test
    fun `layers do not import forbidden implementation details`() {
        productionScope.files
            .filter { it.hasPackage("$basePackage.ui..") }
            .assertTrue { file ->
                file.imports.none { it.name.startsWith("$basePackage.data.") }
            }

        productionScope.files
            .filter { it.hasPackage("$basePackage.domain..") }
            .assertTrue { file ->
                file.imports.none {
                    it.name.startsWith("$basePackage.ui.") ||
                        it.name.startsWith("$basePackage.data.")
                }
            }

        productionScope.files
            .filter { it.hasPackage("$basePackage.data..") }
            .assertTrue { file ->
                file.imports.none { it.name.startsWith("$basePackage.ui.") }
            }
    }

    @Test
    fun `room dao is not accessed outside the data layer and the app entry point`() {
        productionScope.files
            .filterNot {
                it.hasPackage("$basePackage.data..") ||
                    it.hasPackage(basePackage)
            }.assertTrue { file ->
                file.imports.none { it.name.startsWith("$basePackage.data.local.dao.") }
            }
    }

    @Test
    fun `repository interfaces reside in domain and implementations reside in data`() {
        productionScope
            .interfaces()
            .withNameEndingWith("Repository")
            .assertTrue { it.resideInPackage("com.breathofcharge.wattif.domain.repository") }

        productionScope
            .classes()
            .withNameEndingWith("Repository")
            .assertTrue { it.resideInPackage("com.breathofcharge.wattif.data.repository") }
    }

    @Test
    fun `room declarations reside in local data packages`() {
        productionScope
            .interfaces()
            .withNameEndingWith("Dao")
            .assertTrue { it.resideInPackage("com.breathofcharge.wattif.data.local.dao") }

        productionScope
            .classes()
            .withNameEndingWith("Entity")
            .assertTrue { it.resideInPackage("com.breathofcharge.wattif.data.local.entity") }
    }

    @Test
    fun `composable functions use pascal case names`() {
        productionScope
            .functions()
            .filter { it.hasAnnotationWithName("Composable") }
            .assertTrue { function ->
                function.name.firstOrNull()?.isUpperCase() == true
            }
    }

    @Test
    fun `screen source files contain a preview`() {
        productionScope.files
            .filter { it.hasPackage("$basePackage.ui..") && it.name.endsWith("Screen") }
            .assertTrue { file ->
                file.functions().any { it.hasAnnotationWithName("Preview") }
            }
    }
}
