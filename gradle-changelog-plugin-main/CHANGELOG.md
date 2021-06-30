# Gradle Changelog Plugin

## [Unreleased]
### Added

### Changed

### Deprecated

### Removed

### Fixed

### Security
## [1.1.2]
### Changed
- Remove `shadowJar`

### Fixed
- Don't create groups for the new Unreleased section if empty array is provided

## [1.1.1]
### Changed
- Require `changelog.version` to be provided explicitly

### Fixed
- `unspecified` version when patching the changelog

## [1.0.1]
### Fixed
- Provide `project.version` to the extension using conventions

## [1.0.0]
### Added
- Support for the [Configuration cache](https://docs.gradle.org/6.8.1/userguide/configuration_cache.html)

### Changed
- `header` closure has the delegate explicitly set to the extension's context
- Upgrade Gradle Wrapper to `6.6`
- Upgrade `org.jetbrains.kotlin.jvm` to `1.4.21`
- Upgrade `io.gitlab.arturbosch.detekt` to `1.15.0`
- Upgrade `com.github.johnrengelman.shadow` to `6.1.0`

## [0.6.2]
### Changed
- Smart processing of `headerParserRegex` property

## [0.6.1]
### Changed
- Renamed `hasVersion` method to `has`
- Better error handling in `patchChangelog` task

## [0.6.0]
### Added
- `headerParserRegex` extension property for setting custom regex used to extract version from the header string 

### Changed
- Project dependencies upgrade
- Apply ktlint and detekt rules to the code

## [0.5.0]
### Added
- `header` extension property for setting new header text
- `date` helper method
- `Helper Methods` section in README

### Removed
- `headerFormat` and `headerArguments` in favor of `header` property

## [0.4.0]
### Added
- `initializeChangelog` task for creating new changelog file
- `getAll` extension method for fetching all changelog items 
- `groups` extension property for defining the groups created with the Unreleased section
- `ktlint` integration

### Changed
- Move tasks to the `changelog` Gradle group

## [0.3.3]
### Added
- `patchEmpty` extension property
- Better error handling for the header parser
- GitHub Actions integration with itself

### Fixed
- Possibility to write date besides versions #5

### Changed
- `unreleasedTerm` default value set from `Unreleased` to `[Unreleased]`

## [0.3.2]
### Added
- `markdownToHTML` method in `extensions.kt` file
- `markdownToPlainText` method in `extensions.kt` file

## [0.3.1]
### Added
- `--unreleased` flag for the `getChangelog` task

## [0.3.0]
### Added
- Allow maintaining changelog without change note types (Added, Fixed)
- Customising the header by the `patchChangelog` task with `headerArguments` extension's property
- Customising the change notes splitting with the `itemPrefix` extension's property
- More tests

### Changed
- `format` extension property renamed to `headerFormat`

### Fixed
- Avoid parsing the unreleased header
- Invalid change notes splitting

## [0.2.0]
### Added
- Tests for Plugin, Extension and Tasks
- `getHeader() : String` in `Changelog.Item`
- `withFilter(filter: ((String) -> Boolean)?)` in `Changelog.Item`
- `getLatest()` helper method
- `hasVersion(version: String)` helper method

### Changed
- Extract `closure` to `extensions.kt` separated file
- Code enhancements

## [0.1.5]
### Changed
- `changelog.get` and `changelog.getLatest` return `Changelog.Item`
- `noHeader` flag in `Changelog.Item` methods changed to builder pattern
- `Changelog.Item#asHTML` renamed to `Changelog.Item#toHTML` 
- `Changelog.Item#asText` renamed to `Changelog.Item#toText` 

## [0.1.4]
### Fixed
- Remove `org.jetbrains:markdown` dependency from the POM file

## [0.1.3]
### Fixed
- Bundle `org.jetbrains:markdown` dependency with `shadowJar`

## [0.1.0]
### Added
- Initial release
- `get`/`getUnreleased` helper methods
- `changelog` extension configuration
- `getChangelog`/`patchChangelog` tasks
