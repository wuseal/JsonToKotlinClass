package org.jetbrains.changelog.exceptions

class MissingVersionException(version: String?) : Exception("Version is missing: $version")
