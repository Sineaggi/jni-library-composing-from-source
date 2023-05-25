plugins {
	id("java")
	id("dev.nokee.jni-library")
}

description = "The JNI library as the consumer would expect."

library {
	dependencies {
		api(project(":java-jni-greeter"))
		nativeImplementation(project(":cpp-jni-greeter"))
	}
}

repositories {
	mavenCentral()
}

//dependencies {
//	implementation("")
//}

testing {
	suites {
		val test by getting(JvmTestSuite::class) {
			useJUnitJupiter()
		}
	}
}

//tasks.jar {
//
//}
//
//tasks.classes {
//
//}
