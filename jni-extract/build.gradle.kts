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
    commandLine(
        "cmd",
        "/c",
        file(jextractHome.get()).resolve("bin/jextract").absolutePath,
        file("header.h"),
        "-I", file(jdk20Home.get()).resolve("include").absolutePath,
        "-I", file(jdk20Home.get()).resolve("include/win32").absolutePath,
        "--source",
        "--target-package", "com.sineaggi.jniutils.internal.jni",

        "--include-struct", "JNIEnv_",
        "--include-struct", "JNINativeInterface_",
        "--include-constant", "JNI_FALSE",
        "--include-function", "JAWT_GetAWT",

        "--include-struct", "jawt",
        "--include-struct", "jawt_DrawingSurface",
        "--include-struct", "jawt_DrawingSurfaceInfo",
        "--include-struct", "jawt_Win32DrawingSurfaceInfo",
        "--include-constant", "JAWT_VERSION_9",
        "--include-constant", "JAWT_LOCK_ERROR",

        "-l", "jawt",
        "--output", "$buildDir/generated/sources/jextract/java/main/",
    )
}

tasks.compileJava {
    dependsOn(generateJextractSources)
}

sourceSets {
    java {
        main {
            java.srcDir(generatedSourcesDir)
        }
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(20))

tasks.withType<JavaCompile>().configureEach {
    options.release.set(20)
    options.compilerArgs = listOf("--enable-preview")
}
