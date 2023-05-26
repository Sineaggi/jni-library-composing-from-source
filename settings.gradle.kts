pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    plugins {
        id("dev.nokee.jni-library") version "0.4.0"
    }
}

rootProject.name = "jni-library-composing-from-source"

include("java-jni-greeter")
include("cpp-jni-greeter")
include("java-loader")
include("cpp-greeter")
