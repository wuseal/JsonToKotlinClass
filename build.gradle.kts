buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

plugins {
    id("org.jetbrains.intellij") version "0.7.3"
    kotlin("jvm") version "1.4.20"
}
group = "wu.seal"
version = System.getenv("TRAVIS_TAG")

intellij {
    version = "2017.1"
    pluginName = "Json To Kotlin Class"
}
tasks.patchPluginXml {
    untilBuild("")
}
tasks.publishPlugin {
    token(System.getenv("token"))
    channels(System.getProperty("channels", ""))
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.20")
    testImplementation("com.winterbe:expekt:0.5.0") {
        exclude(group = "org.jetbrains.kotlin")
    }
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

