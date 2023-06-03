plugins {
    java
    id("dev.nokee.jni-library")
}

description = "The JNI library as the consumer would expect."

java.toolchain.languageVersion.set(JavaLanguageVersion.of(20))

tasks.withType<JavaCompile>().configureEach {
    options.release = 20
}

library {
    dependencies {
        api(projects.javaJni)
        nativeImplementation(projects.cppJni)
        jvmImplementation(projects.jniExtract)
    }
}

repositories {
    mavenCentral()
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.9.3")
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs = listOf("--enable-preview")
}

tasks.test {
    jvmArgs = listOf("--enable-preview")
}
