package com.github.sineaggi.jniutils;

import com.github.sineaggi.jniutils.JNIUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AWTUtilTest {
    @Test
    public void basicTest() {
        Object o = new Object();
        var ref = JNIUtils.makeGlobalRef(o);
        System.out.println(ref);
        var ref2 = JNIUtils.makeGlobalRef(o);
        System.out.println(ref2);
        var orig = JNIUtils.readGlobalRef(ref);
        var orig2 = JNIUtils.readGlobalRef(ref2);
        System.out.println(o == orig);
        System.out.println(orig == orig2);
        System.out.println(JNIUtils.getEnv());
        System.out.println(JNIUtils.getEnv());
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
}
