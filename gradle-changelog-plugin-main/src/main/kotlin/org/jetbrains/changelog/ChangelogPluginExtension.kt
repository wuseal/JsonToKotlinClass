package org.jetbrains.changelog

import groovy.lang.Closure
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.jetbrains.changelog.exceptions.VersionNotSpecifiedException
import java.io.File
import java.util.regex.Pattern

@Suppress("UnstableApiUsage")
open class ChangelogPluginExtension(objects: ObjectFactory, private val projectDir: File) {

    @Optional
    @Internal
    private val groupsProperty = objects.listProperty(String::class.java).apply {
        set(listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Security"))
    }
    var groups: List<String>
        get() = groupsProperty.getOrElse(emptyList())
        set(value) = groupsProperty.set(value)

    @Optional
    @Internal
    private val headerProperty = objects.property(Closure::class.java).apply {
        set(closure { "[$version]" })
    }
    var header: Closure<*>
        get() = headerProperty.get()
        set(value) = headerProperty.set(value)

    @Optional
    @Internal
    private val headerParserRegexProperty = objects.property(Regex::class.java)
    var headerParserRegex: Any?
        get() = headerParserRegexProperty.orNull
        set(value) = headerParserRegexProperty.set(headerParserRegexHelper(value))

    private fun <T> headerParserRegexHelper(t: T) = when (t) {
        is Regex -> t
        is String -> t.toRegex()
        is Pattern -> t.toRegex()
        else -> throw IllegalArgumentException("Unsupported type of $t. Expected value types: Regex, String, Pattern.")
    }

    @Optional
    @Internal
    private val itemPrefixProperty = objects.property(String::class.java).apply {
        set("-")
    }
    var itemPrefix: String
        get() = itemPrefixProperty.get()
        set(value) = itemPrefixProperty.set(value)

    @Optional
    @Internal
    private val keepUnreleasedSectionProperty = objects.property(Boolean::class.java).apply {
        set(true)
    }
    var keepUnreleasedSection: Boolean
        get() = keepUnreleasedSectionProperty.get()
        set(value) = keepUnreleasedSectionProperty.set(value)

    @Optional
    @Internal
    private val patchEmptyProperty = objects.property(Boolean::class.java).apply {
        set(true)
    }
    var patchEmpty: Boolean
        get() = patchEmptyProperty.get()
        set(value) = patchEmptyProperty.set(value)

    @Optional
    @Internal
    private val pathProperty = objects.property(String::class.java).apply {
        set("$projectDir/CHANGELOG.md")
    }
    var path: String
        get() = pathProperty.get()
        set(value) = pathProperty.set(value)

    @Internal
    private val versionProperty = objects.property(String::class.java)
    var version: String
        get() = versionProperty.run {
            if (isPresent) {
                return get()
            }
            throw VersionNotSpecifiedException()
        }
        set(value) = versionProperty.set(value)

    @Optional
    @Internal
    private val unreleasedTermProperty = objects.property(String::class.java).apply {
        set("[Unreleased]")
    }
    var unreleasedTerm: String
        get() = unreleasedTermProperty.get()
        set(value) = unreleasedTermProperty.set(value)

    fun getUnreleased() = get(unreleasedTerm)

    fun get(version: String) = Changelog(this).get(version)

    fun getLatest() = Changelog(this).getLatest()

    fun getAll() = Changelog(this).getAll()

    fun has(version: String) = Changelog(this).has(version)
}
