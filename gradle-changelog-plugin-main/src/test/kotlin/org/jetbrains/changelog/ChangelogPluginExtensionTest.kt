package org.jetbrains.changelog

import org.jetbrains.changelog.exceptions.MissingFileException
import org.jetbrains.changelog.exceptions.MissingVersionException
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ChangelogPluginExtensionTest : BaseTest() {

    @BeforeTest
    fun localSetUp() {
        changelog =
            """
            # Changelog
            
            ## [Unreleased]
            ### Added
            - Foo
            
            ## [1.0.0]
            ### Removed
            - Bar
            """
    }

    @Test
    fun `throws MissingFileException when changelog file does not exist`() {
        File(extension.path).delete()
        assertFailsWith<MissingFileException> {
            extension.get(version)
        }
    }

    @Test
    fun `throws MissingVersionException if requested version is not available`() {
        assertFailsWith<MissingVersionException> {
            extension.get("2.0.0")
        }
    }

    @Test
    fun `returns change notes for the v1_0_0 version`() {
        extension.get(version).apply {
            assertEquals(project.version, version)

            assertEquals(
                """
                ### Removed
                - Bar
                """.trimIndent(),
                toText()
            )

            assertEquals(
                """
                ### Removed
                - Bar
                """.trimIndent(),
                toString()
            )

            assertEquals(
                """
                <h3>Removed</h3>
                <ul><li>Bar</li></ul>
                """.trimIndent(),
                toHTML()
            )
        }
    }

    @Test
    fun `parses changelog with custom format`() {
        changelog = changelog.replace("""\[([^]]+)]""".toRegex(), "[[$1]]")
        extension.unreleasedTerm = "[[Unreleased]]"
        extension.get(version).apply {
            assertEquals("1.0.0", version)
        }
    }

    @Test
    fun `getUnreleased() returns Unreleased section`() {
        extension.getUnreleased().withHeader(true).apply {
            assertEquals("[Unreleased]", version)
            assertEquals(
                """
                ## [Unreleased]
                ### Added
                - Foo
                """.trimIndent(),
                toText()
            )
        }
    }

    @Test
    fun `getUnreleased() returns Upcoming section if unreleasedTerm is customised`() {
        changelog = changelog.replace("Unreleased", "Upcoming")
        extension.unreleasedTerm = "[Upcoming]"
        extension.getUnreleased().withHeader(true).apply {
            assertEquals("[Upcoming]", version)
            assertEquals(
                """
                ## [Upcoming]
                ### Added
                - Foo
                """.trimIndent(),
                toText()
            )
        }
    }

    @Test
    @Suppress("LongMethod", "MaxLineLength")
    fun `parses changelog into structured sections`() {
        changelog =
            """
            # Changelog
            
            ## [1.0.0]
            ### Added
            - Foo *FOO* foo
            - Bar **BAR** bar
            - Test [link](https://www.example.org) test
            - Code `block` code
            - Bravo
            - Alpha
            
            ### Fixed
            - Hello
            - World
            
            ### Removed
            - Hola
            """

        extension.get(version).apply {
            assertEquals(this@ChangelogPluginExtensionTest.version, version)
            assertEquals("## [1.0.0]", getHeader())
            withHeader(true).getSections().apply {
                assertEquals(3, size)
                assertTrue(containsKey("Added"))
                assertEquals(6, get("Added")?.size)
                assertTrue(containsKey("Fixed"))
                assertEquals(2, get("Fixed")?.size)
                assertTrue(containsKey("Removed"))
                assertEquals(1, get("Removed")?.size)
            }
            assertEquals(
                """
                ## [1.0.0]
                ### Added
                - Foo *FOO* foo
                - Bar **BAR** bar
                - Test [link](https://www.example.org) test
                - Code `block` code
                - Bravo
                - Alpha

                ### Fixed
                - Hello
                - World

                ### Removed
                - Hola
                """.trimIndent(),
                toText()
            )
            assertEquals(
                """
                <h2>[1.0.0]</h2>
                <h3>Added</h3>
                <ul><li>Foo <em>FOO</em> foo</li><li>Bar <strong>BAR</strong> bar</li><li>Test <a href="https://www.example.org">link</a> test</li><li>Code <code>block</code> code</li><li>Bravo</li><li>Alpha</li></ul>
                
                <h3>Fixed</h3>
                <ul><li>Hello</li><li>World</li></ul>
                
                <h3>Removed</h3>
                <ul><li>Hola</li></ul>
                """.trimIndent(),
                toHTML()
            )
            assertEquals(
                """
                [1.0.0]
                Added
                - Foo FOO foo
                - Bar BAR bar
                - Test link test
                - Code block code
                - Bravo
                - Alpha
                
                Fixed
                - Hello
                - World
                
                Removed
                - Hola
                """.trimIndent(),
                toPlainText()
            )
        }
    }

    @Test
    fun `filters out entries from the change notes for the given version`() {
        changelog =
            """
            # Changelog
            
            ## [1.0.0]
            ### Added
            - Foo
            - Bar x
            - Buz
            - Bravo x
            - Alpha
            
            ### Fixed
            - Hello x
            - World
            
            ### Removed
            - Hola x
            """

        extension.get(version).apply {
            assertEquals(this@ChangelogPluginExtensionTest.version, version)
            assertEquals("## [1.0.0]", getHeader())
            withFilter {
                !it.endsWith('x')
            }.getSections().apply {
                assertEquals(2, size)
                assertTrue(containsKey("Added"))
                assertEquals(3, get("Added")?.size)
                assertTrue(containsKey("Fixed"))
                assertEquals(1, get("Fixed")?.size)
                assertFalse(containsKey("Removed"))

                assertEquals(
                    """
                    ### Added
                    - Foo
                    - Buz
                    - Alpha

                    ### Fixed
                    - World
                    """.trimIndent(),
                    toText()
                )
                assertEquals(
                    """
                    <h3>Added</h3>
                    <ul><li>Foo</li><li>Buz</li><li>Alpha</li></ul>

                    <h3>Fixed</h3>
                    <ul><li>World</li></ul>
                    """.trimIndent(),
                    toHTML()
                )
            }
        }
    }

    @Test
    fun `returns latest change note`() {
        extension.getLatest().apply {
            assertEquals("[Unreleased]", version)
            assertEquals("## [Unreleased]", getHeader())
        }
    }

    @Test
    fun `checks if the given version exists in the changelog`() {
        assertTrue(extension.has("[Unreleased]"))
        assertTrue(extension.has("1.0.0"))
        assertFalse(extension.has("2.0.0"))
    }

    @Test
    fun `parses header with custom format containing version and date`() {
        changelog =
            """
            # Changelog
            ## NEW VERSION
            - Compatible with IDEA 2020.2 EAPs
            
            ## Version 1.0.1119-eap (29 May 2020)
            - Compatible with IDEA 2020.2 EAPs
            """

        extension.unreleasedTerm = "NEW VERSION"
        extension.get("1.0.1119-eap").apply {
            assertEquals("1.0.1119-eap", version)
        }
    }

    @Test
    fun `returns change notes without group sections if not present`() {
        changelog =
            """
            # Changelog
            ## [1.0.0]
            - Foo
            """

        extension.get("1.0.0").apply {
            assertEquals("1.0.0", version)

            withHeader(true).getSections().apply {
                assertEquals(1, size)
                assertTrue(containsKey(""))
                assertEquals(1, get("")?.size)
            }
            assertEquals(
                """
                ## [1.0.0]
                - Foo
                """.trimIndent(),
                toText()
            )
            assertEquals(
                """
                <h2>[1.0.0]</h2>
                <ul><li>Foo</li></ul>
                """.trimIndent(),
                toHTML()
            )
        }
    }

    @Test
    fun `splits change notes into a list by the given itemPrefix`() {
        changelog =
            """
            # Changelog
            ## [1.0.0]
            - Foo - bar
            * Foo2
            - Bar
            """

        extension.get("1.0.0").apply {
            assertEquals("1.0.0", version)
            assertEquals(1, getSections().keys.size)
            getSections().values.first().apply {
                assertEquals(2, size)
                assertEquals(
                    """
                    - Foo - bar
                    * Foo2
                    """.trimIndent(),
                    first()
                )
                assertEquals("- Bar", last())
            }
        }
    }

    @Test
    fun `returns all Changelog items`() {
        extension.getAll().apply {
            assertNotNull(this)
            assertEquals(2, keys.size)
            assertEquals("[Unreleased]", keys.first())
            assertEquals("1.0.0", keys.last())
            assertEquals("## [Unreleased]", values.first().getHeader())
            assertEquals("## [1.0.0]", values.last().getHeader())
            assertEquals(
                """
                ### Added
                - Foo
                """.trimIndent(),
                values.first().toText()
            )
            assertEquals(
                """
                ## [Unreleased]
                ### Added
                - Foo
                """.trimIndent(),
                values.first().withHeader(true).toText()
            )
            assertEquals(
                """
                ### Removed
                - Bar
                """.trimIndent(),
                values.last().toText()
            )
            assertEquals(
                """
                ## [1.0.0]
                ### Removed
                - Bar
                """.trimIndent(),
                values.last().withHeader(true).toText()
            )
        }
    }

    @Test
    fun `returns Changelog items for change note without category`() {
        extension.itemPrefix = "*"
        extension.unreleasedTerm = "Unreleased"
        changelog =
            """
            # My Changelog

            ## Unreleased
            
            * Foo
            """

        assertNotNull(extension.getLatest())
        assertEquals(
            """
            <h2>Unreleased</h2>
            <ul><li>Foo</li></ul>
            """.trimIndent(),
            extension.getLatest().withHeader(true).toHTML()
        )
    }

    @Test
    fun `allows to customize the header parser regex to match version in different format than semver`() {
        changelog =
            """
            # My Changelog

            ## 2020.1
            
            * Foo
            """

        extension.headerParserRegex =
            """\d+\.\d+""".toRegex()
        assertNotNull(extension.get("2020.1"))

        extension.headerParserRegex = "\\d+\\.\\d+"
        assertNotNull(extension.get("2020.1"))

        extension.headerParserRegex =
            """\d+\.\d+""".toPattern()
        assertNotNull(extension.get("2020.1"))

        assertFailsWith<IllegalArgumentException> {
            extension.headerParserRegex = 123
            assertNotNull(extension.get("2020.1"))
        }
    }
}
