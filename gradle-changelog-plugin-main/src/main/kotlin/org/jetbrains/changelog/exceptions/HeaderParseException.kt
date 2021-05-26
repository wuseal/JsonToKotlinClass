package org.jetbrains.changelog.exceptions

import org.jetbrains.changelog.ChangelogPluginExtension

class HeaderParseException(value: String, extension: ChangelogPluginExtension) : Exception(
    "Header '$value' does not contain version number. " +
        ("Probably you want set unreleasedTerm to '$value'".takeIf { value.contains(extension.unreleasedTerm) } ?: "")
)
