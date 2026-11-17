package com.breathofcharge.wattif.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class ArchitectureKonsistTest {
    private val basePackage = "com.breathofcharge.wattif"
    private val forbiddenUiDependencies = listOf("$basePackage.data.")
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
                file.imports.none { import ->
                    forbiddenUiDependencies.any(import.name::startsWith)
                } &&
                    !containsForbiddenDependencyReference(
                        source = file.text,
                        forbiddenPrefixes = forbiddenUiDependencies,
                    )
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
                    it.name == "MainActivity"
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
            .classesAndObjects()
            .withNameEndingWith("Repository")
            .assertTrue { it.resideInPackage("com.breathofcharge.wattif.data.repository") }
    }

    @Test
    fun `room declarations reside in local data packages`() {
        productionScope
            .classesAndInterfaces()
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

    @Test
    fun `fully qualified dependency check ignores comments and strings`() {
        val source =
            """
            package $basePackage.ui.main
            // $basePackage.data.repository is mentioned in documentation.
            val label = "$basePackage.data.repository"
            """.trimIndent()

        assert(!containsForbiddenDependencyReference(source, forbiddenUiDependencies))
        assert(
            containsForbiddenDependencyReference(
                source = "val repository = $basePackage.data.repository.Repository()",
                forbiddenPrefixes = forbiddenUiDependencies,
            ),
        )
    }
}

private fun containsForbiddenDependencyReference(
    source: String,
    forbiddenPrefixes: List<String>,
): Boolean =
    stripCommentsAndStrings(source)
        .lineSequence()
        .filterNot { it.trimStart().startsWith("package ") || it.trimStart().startsWith("import ") }
        .any { line -> forbiddenPrefixes.any(line::contains) }

private fun stripCommentsAndStrings(source: String): String {
    val output = StringBuilder(source.length)
    var index = 0
    var blockCommentDepth = 0
    var inLineComment = false
    var stringDelimiter: String? = null
    var inCharacter = false

    while (index < source.length) {
        when {
            inLineComment -> {
                if (source[index] == '\n') {
                    inLineComment = false
                    output.append('\n')
                } else {
                    output.append(' ')
                }
                index++
            }
            blockCommentDepth > 0 -> {
                when {
                    source.startsWith("/*", index) -> {
                        blockCommentDepth++
                        output.append("  ")
                        index += 2
                    }
                    source.startsWith("*/", index) -> {
                        blockCommentDepth--
                        output.append("  ")
                        index += 2
                    }
                    else -> {
                        output.append(if (source[index] == '\n') '\n' else ' ')
                        index++
                    }
                }
            }
            stringDelimiter != null -> {
                if (source.startsWith(stringDelimiter, index)) {
                    output.append(" ".repeat(stringDelimiter.length))
                    index += stringDelimiter.length
                    stringDelimiter = null
                } else if (stringDelimiter == "\"" && source[index] == '\\') {
                    output.append("  ")
                    index += 2
                } else {
                    output.append(if (source[index] == '\n') '\n' else ' ')
                    index++
                }
            }
            inCharacter -> {
                if (source[index] == '\\') {
                    output.append("  ")
                    index += 2
                } else {
                    if (source[index] == '\'') inCharacter = false
                    output.append(' ')
                    index++
                }
            }
            source.startsWith("//", index) -> {
                inLineComment = true
                output.append("  ")
                index += 2
            }
            source.startsWith("/*", index) -> {
                blockCommentDepth = 1
                output.append("  ")
                index += 2
            }
            source.startsWith("\"\"\"", index) -> {
                stringDelimiter = "\"\"\""
                output.append("   ")
                index += 3
            }
            source[index] == '"' -> {
                stringDelimiter = "\""
                output.append(' ')
                index++
            }
            source[index] == '\'' -> {
                inCharacter = true
                output.append(' ')
                index++
            }
            else -> {
                output.append(source[index])
                index++
            }
        }
    }

    return output.toString()
}
