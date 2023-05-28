plugins {
    id("java")
    id("dev.nokee.jni-library")
}

description = "The JNI library as the consumer would expect."

library {
    dependencies {
        api(project(":java-jni"))
        nativeImplementation(project(":cpp-jni"))
    }
}

repositories {
    mavenCentral()
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
    }
}
