plugins {
    java
    id("dev.nokee.jni-library")
}

description = "The JNI library as the consumer would expect."

java.toolchain.languageVersion = JavaLanguageVersion.of(20)

tasks.withType<JavaCompile>().configureEach {
    options.release = 20
}

library {
    dependencies {
        api(projects.javaJni)
        nativeImplementation(projects.cppJni)
    }
}

repositories {
    mavenCentral()
}

testing {
    suites {
        named<JvmTestSuite>("test") {
            useJUnitJupiter("5.9.3")
            dependencies {
                implementation(projects.jniExtract)
            }
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs = listOf("--enable-preview")
}

tasks.test {
    jvmArgs = listOf("--enable-preview")
}
