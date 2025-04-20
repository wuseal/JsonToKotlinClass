import org.hildan.github.changelog.builder.DEFAULT_TIMEZONE
import org.hildan.github.changelog.builder.SectionDefinition
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.concurrent.TimeUnit

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

plugins {
    id("org.jetbrains.intellij") version "1.13.3"
    kotlin("jvm") version "1.8.20"
    id("org.hildan.github.changelog") version "1.6.0"
    java
}
group = "wu.seal"
// Determine version based on Git tags or environment variable
version = System.getenv("TAG") ?: run {
    // Execute git command to check if current commit has a tag
    val gitTagCommand = "git describe --exact-match --tags HEAD".execute()
    val gitLatestTagCommand = "git describe --tags --abbrev=0".execute()
    
    if (gitTagCommand.exitValue() == 0) {
        // Current commit is exactly at a tag - use the tag name
        gitTagCommand.text.trim()
    } else {
        // Current commit is not at a tag - use "Unreleased"
        "Unreleased"
    }
}

intellij {
    version.set("2023.3.5")
    type.set("IU")
    pluginName.set("JsonToKotlinClass")
}

// Add a function to convert Markdown to HTML for our specific changelog format
fun formatChangelogToHtml(markdown: String): String {
    // Extract the version information
    val versionPattern = """## \[(.*?)\](?:\((.*?)\))?(?: \((.*?)\))?""".toRegex()
    val versionMatch = versionPattern.find(markdown)
    
    var html = "<h2>Version ${versionMatch?.groupValues?.get(1) ?: "Latest"}</h2>"
    
    // Add release date if available
    if (versionMatch != null && versionMatch.groupValues.size > 3 && versionMatch.groupValues[3].isNotEmpty()) {
        html += "<p><b>Released:</b> ${versionMatch.groupValues[3]}</p>"
    }
    
    // Add link to commits if available
    val commitsPattern = """\[View commits\]\((.*?)\)""".toRegex()
    val commitsMatch = commitsPattern.find(markdown)
    if (commitsMatch != null) {
        html += "<p><a href=\"${commitsMatch.groupValues[1]}\">View commits</a></p>"
    }
    
    // Process each section (Bugfix, Enhancement, Features)
    val sections = listOf("Bugfix", "Enhancement", "Features")
    
    for (section in sections) {
        val sectionPattern = """(?:\*\*$section\*\*)(.*?)(?:\*\*|$)""".toRegex(RegexOption.DOT_MATCHES_ALL)
        val sectionMatch = sectionPattern.find(markdown)
        
        if (sectionMatch != null) {
            html += "<h3>$section</h3><ul>"
            
            // Extract list items (lines starting with "- ")
            val content = sectionMatch.groupValues[1]
            val lines = content.split("\n")
            
            var currentItem = ""
            
            for (line in lines) {
                val trimmed = line.trim()
                if (trimmed.startsWith("-")) {
                    // If we have accumulated an item before, add it
                    if (currentItem.isNotEmpty()) {
                        var itemText = currentItem.trim()
                        
                        // Convert Markdown links to HTML links
                        val linkPattern = """\[(.*?)\]\((.*?)\)""".toRegex()
                        itemText = itemText.replace(linkPattern) { matchResult ->
                            val text = matchResult.groupValues[1]
                            val url = matchResult.groupValues[2]
                            "<a href=\"$url\">$text</a>"
                        }
                        
                        // Convert escaped characters
                        itemText = itemText.replace("\\[", "[").replace("\\]", "]").replace("\\(", "(").replace("\\)", ")")
                        
                        html += "<li>$itemText</li>"
                    }
                    
                    // Start a new item with the current line (minus the leading dash)
                    currentItem = trimmed.substring(1).trim()
                } else if (trimmed.isNotEmpty() && currentItem.isNotEmpty()) {
                    // Continue accumulating the current item with additional line
                    currentItem += " " + trimmed
                }
            }
            
            // Don't forget to add the last item if there is one
            if (currentItem.isNotEmpty()) {
                var itemText = currentItem.trim()
                
                // Convert Markdown links to HTML links
                val linkPattern = """\[(.*?)\]\((.*?)\)""".toRegex()
                itemText = itemText.replace(linkPattern) { matchResult ->
                    val text = matchResult.groupValues[1]
                    val url = matchResult.groupValues[2]
                    "<a href=\"$url\">$text</a>"
                }
                
                // Convert escaped characters
                itemText = itemText.replace("\\[", "[").replace("\\]", "]").replace("\\(", "(").replace("\\)", ")")
                
                html += "<li>$itemText</li>"
            }
            
            html += "</ul>"
        }
    }
    
    // Add a link to the full changelog
    html += "<p><a href=\"https://github.com/wuseal/JsonToKotlinClass/blob/master/doc/CHANGELOG.md\">View full changelog</a></p>"
    
    return html
}

// Update the patchPluginXml task to use our improved HTML conversion
tasks.patchPluginXml {
    untilBuild.set("")
    // Extract appropriate version's changes and convert to HTML
    changeNotes.set(
        try {
            val changelogFile = File("${project.projectDir}/doc/CHANGELOG.md")
            val changelogText = changelogFile.readText()
            val currentVersion = project.version.toString()
            val isReleaseVersion = currentVersion != "Unreleased"
            
            // First find the section that matches our current version
            val sectionHeader = if (isReleaseVersion) "## [$currentVersion]" else "## [Unreleased]"
            val sectionStart = changelogText.indexOf(sectionHeader)
            
            val markdownNotes = if (sectionStart != -1) {
                // Find the start of the next section
                val nextSectionStart = changelogText.indexOf("## ", sectionStart + sectionHeader.length)
                
                // Extract just this version's section
                if (nextSectionStart != -1) {
                    changelogText.substring(sectionStart, nextSectionStart).trim()
                } else {
                    // This is the only/last section
                    changelogText.substring(sectionStart).trim()
                }
            } else {
                // Section not found - show just a simple message
                if (isReleaseVersion) "## [$currentVersion] (2023-09-01)" else "## [Unreleased]"
            }
            
            // Convert to HTML using our improved function
            formatChangelogToHtml(markdownNotes)
        } catch (e: Exception) {
            "<p>See <a href=\"https://github.com/wuseal/JsonToKotlinClass/blob/master/doc/CHANGELOG.md\">GitHub</a> for changelog</p>"
        }
    )
}

tasks.publishPlugin {
    token.set(System.getenv("token") ?: "")
    channels.set(listOf(System.getProperty("channels", "")))
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // Mark Kotlin stdlib as "compileOnly" to avoid duplication with the version provided by the IntelliJ Platform
    compileOnly(kotlin("stdlib"))
    testImplementation("com.winterbe:expekt:0.5.0") {
        exclude(group = "org.jetbrains.kotlin")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType(KotlinCompile::class.java).configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}

tasks.buildSearchableOptions {
    enabled = false
}

// Add back the GitHub changelog configuration
changelog {
    githubUser = "wuseal"
    githubRepository = rootProject.name
    githubToken = findProperty("githubToken")?.toString() ?: (System.getenv("GH_TOKEN")?.toString())
    title = "Change Log"
    showUnreleased = true
    unreleasedVersionTitle = "Unreleased"
    
    val currentVersion = project.version.toString()
    if (currentVersion != "Unreleased" && !System.getenv("TAG").isNullOrEmpty()) {
        println("TAG is ${System.getenv("TAG")}, Set future version to $currentVersion")
        futureVersionTag = currentVersion
    }
    
    sections = listOf(
        SectionDefinition("Features", "feature request"),
        SectionDefinition("Bugfix", listOf("bug", "bug fix")),
        SectionDefinition("Enhancement", "enhancement")
    )
    includeLabels = listOf("feature request", "bug", "bug fix", "enhancement")
    excludeLabels = listOf("duplicate", "invalid", "question", "wontfix")
    sinceTag = "V3.0.0"
    skipTags = listOf()
    useMilestoneAsTag = true
    timezone = DEFAULT_TIMEZONE

    outputFile = file("${projectDir}/doc/CHANGELOG.md")
}

// Custom task to generate GitHub release notes
task("createGithubReleaseNotes") {
    doLast {
        val githubReleaseNoteFile = file("./githubReleaseNote.md")
        val content = try {
            "**" + file("${projectDir}/doc/CHANGELOG.md").readText()
                .substringAfter("**").substringBefore("##").trim()
        } catch (e: Exception) {
            "No changelog available"
        }
        githubReleaseNoteFile.writeText(content)
    }
}

// Create a custom task to ensure the changelog is generated before building the plugin
tasks.register("ensureChangelogGenerated") {
    doLast {
        // Get the current version
        val currentVersion = project.version.toString()
        val isReleaseVersion = currentVersion != "Unreleased"
        
        if (!file("${projectDir}/doc/CHANGELOG.md").exists() || gradle.startParameter.taskNames.contains("generateChangelog")) {
            // If the file doesn't exist or a regeneration is explicitly requested, generate it
            tasks.named("generateChangelog").get().actions.forEach { it.execute(tasks.named("generateChangelog").get()) }
        }
        
        // If we're building an "Unreleased" version and have changes since the last tag,
        // make sure the Unreleased section exists in the changelog
        if (!isReleaseVersion) {
            val changelogFile = file("${projectDir}/doc/CHANGELOG.md")
            val changelogContent = if (changelogFile.exists()) changelogFile.readText() else ""
            
            if (!changelogContent.contains("## [Unreleased]")) {
                // If there's no Unreleased section, add it at the top of the changelog
                val updatedContent = "# Change Log\n\n## [Unreleased]\n\n" + 
                        (if (changelogContent.contains("# Change Log")) 
                            changelogContent.substringAfter("# Change Log").trim() 
                        else changelogContent.trim())
                changelogFile.writeText(updatedContent)
            }
        }
    }
}

tasks.getByName("buildPlugin").dependsOn("ensureChangelogGenerated")

// Extension function to execute shell commands
fun String.execute(): Process {
    val parts = this.split("\\s".toRegex())
    val process = ProcessBuilder(*parts.toTypedArray())
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()
    
    process.waitFor(10, TimeUnit.SECONDS)
    return process
}

// Extension property to get process output as text
val Process.text: String
    get() = inputStream.bufferedReader().readText().trim()
