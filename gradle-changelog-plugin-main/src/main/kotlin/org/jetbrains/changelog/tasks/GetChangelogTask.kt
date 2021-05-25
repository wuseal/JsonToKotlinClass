package org.jetbrains.changelog.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.ChangelogPluginExtension
import java.io.File

open class GetChangelogTask : DefaultTask() {

    private val extension = project.extensions.getByType(ChangelogPluginExtension::class.java)

    private var noHeader = false

    @Suppress("UnstableApiUsage")
    @Option(option = "no-header", description = "Omits header version line")
    fun setNoHeader(noHeader: Boolean) {
        this.noHeader = noHeader
    }

    @Input
    fun getNoHeader() = noHeader

    private var unreleased = false

    @Suppress("UnstableApiUsage")
    @Option(option = "unreleased", description = "Returns Unreleased change notes")
    fun setUnreleased(unreleased: Boolean) {
        this.unreleased = unreleased
    }

    @Input
    fun getUnreleased() = unreleased

    @InputFile
    fun getInputFile() = File(extension.path)

    @OutputFile
    fun getOutputFile() = getInputFile()

    @TaskAction
    fun run() = logger.quiet(
        Changelog(extension).run {
            val version = when (unreleased) {
                true -> extension.unreleasedTerm
                false -> extension.version
            }
            get(version).run {
                withHeader(!noHeader)
                toText()
            }
        }
    )
}
