pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    plugins {
        id("dev.nokee.jni-library") version "0.4.0"
    }
}

rootProject.name = "jniutils"

include("java-jni")
include("cpp-jni")
include("java-loader")
