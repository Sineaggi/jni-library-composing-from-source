# jniutils

Based off of [this](https://mail.openjdk.org/pipermail/panama-dev/2023-May/019079.html) response on the pamama-dev mailing list.

Exposes the following functions
```java
public class JNIUtils {
    // snip
    public static native long makeGlobalRef(Object o); // call NewGlobalRef, cast result to jlong and return
    public static native void destroyGlobalRef(long ref); // cast to jobject, then call DeleteGlobalRef
    public static native Object readGlobalRef(long ref); // cast to jobject, return
    public static native long getEnv(); // cast env parameter to jlong and return

}
```

Example usage
```java
class Example {
    int version() {
        var env = JNIUtils.getEnv();

        var jniEnv = JNIEnv_.ofAddress(MemorySegment.ofAddress(env), SegmentScope.global());
        var jniNativeInterface = JNIEnv_.functions$get(jniEnv);
        var getVersion = JNINativeInterface_.GetVersion(jniNativeInterface, SegmentScope.global());
        var version = getVersion.apply(jniEnv);
    }
}
```

## Building

Set `jdk20_home` and `jextract_home` gradle properties then run `./gradlew build`
