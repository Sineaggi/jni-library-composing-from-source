package com.sineaggi.jniutils.internal.jnifns;

import com.sineaggi.jniutils.internal.jni.JNIEnv_;
import com.sineaggi.jniutils.internal.jni.JNINativeInterface_;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentScope;

public class EnvFuns {
    public static int version(long env) {
        var jniEnv = JNIEnv_.ofAddress(MemorySegment.ofAddress(env), SegmentScope.global());
        var jniNativeInterface = JNIEnv_.functions$get(jniEnv);
        var getVersion = JNINativeInterface_.GetVersion(jniNativeInterface, SegmentScope.global());
        return getVersion.apply(jniEnv);
    }
}
