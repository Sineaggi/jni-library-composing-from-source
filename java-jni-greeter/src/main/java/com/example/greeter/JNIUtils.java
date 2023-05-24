
package com.example.greeter;

import java.awt.Component;
import java.awt.Graphics;

public class JNIUtils {

    static {
        NativeLoader.loadLibrary(JNIUtils.class.getClassLoader(), "cpp-jni-greeter");
    }

    public static native long makeGlobalRef(Object o); // call NewGlobalRef, cast result to jlong and return
    public static native void destroyGlobalRef(long ref); // cast to jobject, then call DeleteGlobalRef
    public static native Object readGlobalRef(long ref); // cast to jobject, return
    public static native long getEnv(); // cast env parameter to jlong and return

}
