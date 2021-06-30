import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-gradle-plugin")
    id("maven-publish")
    id("org.jetbrains.changelog") version "1.1.2"
    id("org.jetbrains.kotlin.jvm") version "1.5.0"
    id("com.gradle.plugin-publish") version "0.14.0"
    id("io.gitlab.arturbosch.detekt") version "1.16.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    id("com.github.breadmoirai.github-release") version "2.2.12"
}

description = "Gradle Changelog Plugin"
group = "org.jetbrains.intellij.plugins"
version = "1.1.2"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains:markdown:0.2.3")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.16.0")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

gradlePlugin {
    plugins.create("changelog") {
        id = "org.jetbrains.changelog"
        implementationClass = "org.jetbrains.changelog.ChangelogPlugin"
        displayName = "Gradle Changelog Plugin"
        description = "Provides tasks and helper methods for handling changelog in the Project."
    }
}

pluginBundle {
    website = "https://github.com/JetBrains/${project.name}"
    vcsUrl = "https://github.com/JetBrains/${project.name}.git"
    description = "Gradle Changelog Plugin"
    tags = listOf("changelog", "jetbrains")
}

changelog {
    version = "${project.version}"
}

detekt {
    config.from(file("detekt.yml"))
    buildUponDefaultConfig = true
    parallel = true

    reports {
        html.enabled = false
        xml.enabled = false
        txt.enabled = false
    }
}

tasks {
    listOf("compileKotlin", "compileTestKotlin").forEach {
        getByName<KotlinCompile>(it) {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    withType<Detekt>().configureEach {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}
