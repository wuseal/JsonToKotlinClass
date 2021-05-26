package org.jetbrains.changelog.exceptions

class MissingFileException(path: String) : Exception("Changelog file does not exist: $path")
