package org.jetbrains.changelog

import org.jetbrains.changelog.exceptions.VersionNotSpecifiedException
import org.jetbrains.changelog.tasks.GetChangelogTask
import org.jetbrains.changelog.tasks.InitializeChangelogTask
import org.jetbrains.changelog.tasks.PatchChangelogTask
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ChangelogPluginTest : BaseTest() {

    @Test
    fun `default properties values`() {
        assertNotNull(extension)
        assertTrue(extension.keepUnreleasedSection)
        assertEquals("${project.projectDir}/CHANGELOG.md", extension.path)
        assertEquals("[Unreleased]", extension.unreleasedTerm)
    }

    @Test
    fun `throws VersionNotSpecifiedException when changelog extension has no version provided`() {
        assertFailsWith<VersionNotSpecifiedException> {
            extension.version
        }

        extension.version = "1.0.0"
        assertEquals("1.0.0", extension.version)
    }

    @Test
    fun `tasks availability`() {
        (project.tasks.findByName("initializeChangelog") as InitializeChangelogTask).apply {
            assertNotNull(this)
        }

        (project.tasks.findByName("getChangelog") as GetChangelogTask).apply {
            assertNotNull(this)
            assertEquals(File("${project.projectDir}/CHANGELOG.md").path, getInputFile().path)
        }

        (project.tasks.findByName("patchChangelog") as PatchChangelogTask).apply {
            assertNotNull(this)
        }
    }
}
