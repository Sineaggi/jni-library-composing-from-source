plugins {
	id("java-library")
}

description = "The JNI classes, also known as the JVM bindings."

dependencies {
	implementation(project(":java-loader"))
}

val generatedHeaders by configurations.creating {
	isCanBeConsumed = true
	isCanBeResolved = false
	// If you want this configuration to share the same dependencies, otherwise omit this line
	//extendsFrom(configurations["implementation"], configurations["runtimeOnly"])
}

artifacts {
	add(generatedHeaders.name, file("${buildDir}/generated/sources/headers/java/main")) {
		builtBy(tasks.classes)
	}
}
