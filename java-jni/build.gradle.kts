plugins {
    `java-library`
}

description = "The JNI classes, also known as the JVM bindings."

dependencies {
    implementation(projects.javaLoader)
}

val generatedHeaders by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

artifacts {
    add(generatedHeaders.name, tasks.compileJava.flatMap { it.options.headerOutputDirectory })
}
