package org.jetbrains.changelog.tasks

import org.jetbrains.changelog.BaseTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetChangelogTaskTest : BaseTest() {

    @BeforeTest
    fun localSetUp() {
        changelog =
            """
            # Changelog
            ## [Unreleased]
            - bar
            ## [1.0.0]
            ### Added
            - foo
            """

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
    fun `returns change notes for the version specified with extension`() {
        val result = runTask("getChangelog", "--quiet")

        assertEquals(
            """
            ## [1.0.0]
            ### Added
            - foo
            """.trimIndent(),
            result.output.trim()
        )
    }

    @Test
    fun `returns the Unreleased change notes`() {
        val result = runTask("getChangelog", "--quiet", "--unreleased")

        assertEquals(
            """
            ## [Unreleased]
            - bar
            """.trimIndent(),
            result.output.trim()
        )
    }

    @Test
    fun `returns change notes without header for the version specified with extension`() {
        val result = runTask("getChangelog", "--quiet", "--no-header")

        assertEquals(
            """
            ### Added
            - foo
            """.trimIndent(),
            result.output.trim()
        )
    }

    @Test
    fun `returns change notes with Pattern set to headerParserRegex`() {
        buildFile =
            """
            plugins {
                id 'org.jetbrains.changelog'
            }
            changelog {
                version = "1.0.0"
                headerParserRegex = ~/\d\.\d\.\d/
            }
            """

        project.evaluate()

        runTask("getChangelog")
    }

    @Test
    fun `returns change notes with String set to headerParserRegex`() {
        buildFile =
            """
            plugins {
                id 'org.jetbrains.changelog'
            }
            changelog {
                version = "1.0.0"
                headerParserRegex = "\\d\\.\\d\\.\\d"
            }
            """

        project.evaluate()

        runTask("getChangelog")
    }

    @Test
    fun `fails with Integer set to headerParserRegex`() {
        buildFile =
            """
            plugins {
                id 'org.jetbrains.changelog'
            }
            changelog {
                version = "1.0.0"
                headerParserRegex = 123
            }
            """

        project.evaluate()

        runFailingTask("getChangelog")
    }

    @Test
    fun `throws VersionNotSpecifiedException when changelog extension has no version provided`() {
        buildFile =
            """
            plugins {
                id 'org.jetbrains.changelog'
            }
            changelog {
            }
            """

        project.evaluate()

        val result = runFailingTask("getChangelog")

        assertTrue(
            result.output.contains(
                "org.jetbrains.changelog.exceptions.VersionNotSpecifiedException: Version is missing. " +
                    "Please provide the project version to the `changelog.version` property explicitly."
            )
        )
    }

    @Test
    fun `task loads from the configuration cache`() {
        runTask("getChangelog", "--configuration-cache")
        val result = runTask("getChangelog", "--configuration-cache")

        assertTrue(result.output.contains("Reusing configuration cache."))
    }
}
