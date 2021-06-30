package org.jetbrains.changelog.tasks

import org.jetbrains.changelog.BaseTest
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class InitializeChangelogTaskTest : BaseTest() {

    @BeforeTest
    fun localSetUp() {
        buildFile =
            """
            plugins {
                id 'org.jetbrains.changelog'
            }
            changelog {
                version = "1.0.0"
            }
            """

        project.evaluate()
    }

    @Test
    fun `creates new changelog file`() {
        runTask("initializeChangelog")

        extension.getAll().apply {
            assertEquals(1, keys.size)
            assertEquals("[Unreleased]", keys.first())
            assertEquals(
                """
                ## [Unreleased]
                ### Added
                - Example item

                ### Changed

                ### Deprecated

                ### Removed

                ### Fixed

                ### Security
                """.trimIndent(),
                values.first().withHeader(true).toText()
            )
        }

        assertNotNull(extension.getUnreleased())
    }

    @Test
    fun `overrides existing changelog file`() {
        changelog =
            """
            # Changelog
            """
        project.evaluate()

        runTask("initializeChangelog")

        assertEquals(
            """
            ## [Unreleased]
            ### Added
            - Example item

            ### Changed

            ### Deprecated

            ### Removed

            ### Fixed

            ### Security
            """.trimIndent(),
            extension.getUnreleased().withHeader(true).toText()
        )
    }

    @Test
    fun `creates customized changelog file`() {
        buildFile =
            """
            plugins {
                id 'org.jetbrains.changelog'
            }
            changelog {
                version = "1.0.0"
                path = "${File("${project.projectDir}/CHANGES.md").path.replace("\\", "\\\\")}"
                itemPrefix = "*"
                unreleasedTerm = "Upcoming version"
                groups = ["Added", "Removed"]
            }
            """
        extension.apply {
            path = File("${project.projectDir}/CHANGES.md").path
            unreleasedTerm = "Upcoming version"
            itemPrefix = "*"
        }
        project.evaluate()

        runTask("initializeChangelog")

        assertEquals(
            """
            ## Upcoming version
            ### Added
            * Example item
            
            ### Removed
            """.trimIndent(),
            extension.getUnreleased().withHeader(true).toText()
        )
    }

    @Test
    fun `doesn't throw VersionNotSpecifiedException when changelog extension has no version provided`() {
        buildFile =
            """
            plugins {
                id 'org.jetbrains.changelog'
            }
            changelog {
            }
            """

        project.evaluate()

        val result = runTask("initializeChangelog")

        assertFalse(result.output.contains("VersionNotSpecifiedException"))
    }

    @Test
    fun `task loads from the configuration cache`() {
        runTask("initializeChangelog", "--configuration-cache")
        val result = runTask("initializeChangelog", "--configuration-cache")

        assertTrue(result.output.contains("Reusing configuration cache."))
    }
}
