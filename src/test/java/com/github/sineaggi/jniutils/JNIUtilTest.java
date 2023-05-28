package com.github.sineaggi.jniutils;

import com.sineaggi.jniutils.internal.jni.JNIEnv_;
import com.sineaggi.jniutils.internal.jni.JNINativeInterface_;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentScope;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JNIUtilTest {

    @Test
    public void testRoundTrip() {
        Object o = new Object();
        var ref = JNIUtils.makeGlobalRef(o);
        var orig = JNIUtils.readGlobalRef(ref);
        assertEquals(o, orig);
        JNIUtils.destroyGlobalRef(ref);
    }

    @Test
    public void basicTest() {
        Object o = new Object();
        var ref = JNIUtils.makeGlobalRef(o);
        var ref2 = JNIUtils.makeGlobalRef(o);
        var orig = JNIUtils.readGlobalRef(ref);
        var orig2 = JNIUtils.readGlobalRef(ref2);
        assertEquals(o, orig);
        assertEquals(orig, orig2);
        JNIUtils.destroyGlobalRef(ref);
        JNIUtils.destroyGlobalRef(ref2);
    }

    @Test
    public void testFree() {
        Object o = new Object();
        var ref = JNIUtils.makeGlobalRef(o);
        var read = JNIUtils.readGlobalRef(ref);
        assertEquals(o, read);
        JNIUtils.destroyGlobalRef(ref);
        var freed = JNIUtils.readGlobalRef(ref);
        assertNull(freed);
    }

    @Test
    public void testGetEnv() {
        var env = JNIUtils.getEnv();

        var jniEnv = JNIEnv_.ofAddress(MemorySegment.ofAddress(env), SegmentScope.global());
        var jniNativeInterface = JNIEnv_.functions$get(jniEnv);
        var getVersion = JNINativeInterface_.GetVersion(jniNativeInterface, SegmentScope.global());
        var version = getVersion.apply(jniEnv);

        assertEquals(Runtime.version().feature(), (short)(version >> 16));
    }
}
