package org.jetbrains.changelog.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jetbrains.changelog.ChangelogPluginExtension
import java.io.File

open class InitializeChangelogTask : DefaultTask() {

    private val extension = project.extensions.getByType(ChangelogPluginExtension::class.java)

    @TaskAction
    fun run() {
        File(extension.path).apply {
            if (!exists()) {
                createNewFile()
            }
        }.writeText(
            """
                # Changelog
                
                ## ${extension.unreleasedTerm}
                ### ${extension.groups.first()}
                ${extension.itemPrefix} Example item
                
            """.trimIndent() + extension.groups.drop(1).joinToString("\n") { "### $it\n" }
        )
    }
}
