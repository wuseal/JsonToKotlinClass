package org.jetbrains.changelog.exceptions

class VersionNotSpecifiedException : Exception(
    "Version is missing. Please provide the project version to the `changelog.version` property explicitly."
)
