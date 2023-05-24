import org.gradle.internal.jvm.Jvm

plugins {
	id("cpp-library")
}

description = "The JNI shared library, also known as the native bindings."

library {
	// The native component of the JNI library needs to be a shared library.
	linkage.set(listOf(Linkage.SHARED))
	dependencies {
		implementation(project(":cpp-greeter"))
	}

	binaries.configureEach {
		compileTask.get().includes.from(compileTask.get().targetPlatform.map {
			listOf(File("${Jvm.current().javaHome.canonicalPath}/include")) + when {
				it.operatingSystem.isMacOsX -> listOf(File("${Jvm.current().javaHome.absolutePath}/include/darwin"))
				it.operatingSystem.isLinux -> listOf(File("${Jvm.current().javaHome.absolutePath}/include/linux"))
				it.operatingSystem.isWindows -> listOf(File("${Jvm.current().javaHome.absolutePath}/include/win32"))
				else -> emptyList()
			}
		})
		compileTask.get().isPositionIndependentCode = true
	}
}

val instrumentedClasspath by configurations.creating {
	isCanBeConsumed = false
	isCanBeResolved = true
}

dependencies {
	instrumentedClasspath(project(mapOf(
		"path" to ":java-jni-greeter",
		"configuration" to "generatedHeaders")))
}
