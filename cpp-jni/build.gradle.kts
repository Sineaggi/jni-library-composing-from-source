import org.gradle.internal.jvm.Jvm

plugins {
    `cpp-library`
}

description = "The JNI shared library, also known as the native bindings."

library {
    // The native component of the JNI library needs to be a shared library.
    linkage.set(listOf(Linkage.SHARED))

    baseName.set("jniutils")

    dependencies {
    }

    binaries.configureEach {
        with(compileTask.get()) {
            includes.from(targetPlatform.map {
                listOf(File("${Jvm.current().javaHome.canonicalPath}/include")) + when {
                    it.operatingSystem.isMacOsX -> listOf(File("${Jvm.current().javaHome.absolutePath}/include/darwin"))
                    it.operatingSystem.isLinux -> listOf(File("${Jvm.current().javaHome.absolutePath}/include/linux"))
                    it.operatingSystem.isWindows -> listOf(File("${Jvm.current().javaHome.absolutePath}/include/win32"))
                    else -> emptyList()
                }
            })
            isPositionIndependentCode = true
            compilerArgs.addAll(targetPlatform.map {
                when {
                    it.operatingSystem.isMacOsX -> listOf("-arch", "arm64", "-arch", "x86_64")
                    it.operatingSystem.isLinux -> emptyList()
                    it.operatingSystem.isWindows -> emptyList()
                    else -> emptyList()
                }
            })
            includes.from(generatedHeaders)
        }
    }
}

tasks.withType<LinkSharedLibrary>().configureEach {
    linkerArgs.addAll(
        targetPlatform.map {
            when {
                it.operatingSystem.isMacOsX -> listOf("-arch", "arm64", "-arch", "x86_64")
                it.operatingSystem.isLinux -> emptyList()
                it.operatingSystem.isWindows -> emptyList()
                else -> emptyList()
            }
        }
    )
}

val generatedHeaders by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    generatedHeaders(project(path = ":java-jni", configuration = "generatedHeaders"))
}
