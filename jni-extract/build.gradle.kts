plugins {
    `java-library`
}

val generatedSourcesDir = layout.buildDirectory.dir("generated/sources/jextract/java/main")

val generateJextractSources by tasks.registering(Exec::class) {
    outputs.dir(generatedSourcesDir)
    val jextractHome = providers.gradleProperty("jextract_home")
    inputs.dir(jextractHome)
    val jdk20Home = providers.gradleProperty("jdk20_home")
    inputs.dir(jdk20Home)
    inputs.file(layout.projectDirectory.file("includes.txt"))
    commandLine(
        "cmd",
        "/c",
        file(jextractHome.get()).resolve("bin/jextract").absolutePath,
        file("header.h"),
        "-I", file(jdk20Home.get()).resolve("include").absolutePath,
        "-I", file(jdk20Home.get()).resolve("include/win32").absolutePath,
        "--source",
        "--target-package", "com.sineaggi.jniutils.internal.jni",
        "@includes.txt",
        "-l", "jawt",
        "--output", "$projectDir/src/main/java/",
    )
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(20))

tasks.withType<JavaCompile>().configureEach {
    options.release = 20
    options.compilerArgs = listOf("--enable-preview")
}
