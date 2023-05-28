pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    plugins {
        id("dev.nokee.jni-library") version "0.4.0"
    }
}

rootProject.name = "jniutils"

include("cpp-jni")
include("java-jni")
include("java-loader")
include("jni-extract")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
