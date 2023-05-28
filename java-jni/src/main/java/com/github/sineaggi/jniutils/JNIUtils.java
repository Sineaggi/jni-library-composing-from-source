
package com.github.sineaggi.jniutils;

public class JNIUtils {

    static {
        NativeLoader.loadLibrary(JNIUtils.class.getClassLoader(), "jniutils");
    }

    public static native long makeGlobalRef(Object o); // call NewGlobalRef, cast result to jlong and return
    public static native void destroyGlobalRef(long ref); // cast to jobject, then call DeleteGlobalRef
    public static native Object readGlobalRef(long ref); // cast to jobject, return
    public static native long getEnv(); // cast env parameter to jlong and return

}
