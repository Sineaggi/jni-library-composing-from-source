package com.github.sineaggi.jniutils;

import com.sineaggi.jniutils.internal.jni.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.SegmentScope;

import static com.sineaggi.jniutils.internal.jni.header_h.*;

public class AWTUtilTest {

    @Test
    public void basicTest() {
        Component component;
        if (true) {
            Frame f = new Frame();
            f.setSize(500, 500);
            f.setVisible(true);
            component = f;
        } else {
            JFrame f = new JFrame("Swing Paint Demo");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(250, 250);
            f.setVisible(true);
            component = f;
        }

        var ref = JNIUtils.makeGlobalRef(component);
        var componentRef = MemorySegment.ofAddress(ref);

        var env = JNIUtils.getEnv();
        var jniEnv = JNIEnv_.ofAddress(MemorySegment.ofAddress(env), SegmentScope.global());
        var jniNativeInterface = JNIEnv_.functions$get(jniEnv);

        try (Arena arena = Arena.openConfined()) {
            var alloc = SegmentAllocator.nativeAllocator(arena.scope());
            var jawtt = jawt.allocate(alloc);
            jawt.version$set(jawtt, JAWT_VERSION_9());
            if (JAWT_GetAWT(jniNativeInterface, jawtt) == JNI_FALSE()) {
                throw new RuntimeException("Failed to get awt");
            }
            var getDrawingSurface = jawt.GetDrawingSurface(jawtt, SegmentScope.global());
            var ds = getDrawingSurface.apply(jniEnv, componentRef);
            if (ds.equals(MemorySegment.NULL)) {
                throw new RuntimeException("NULL drawing surface");
            }
            //System.out.printf("our DrawingSurface addr %016X\n", ds.address());
            var lock = jawt_DrawingSurface.Lock(ds, SegmentScope.global());
            if ((lock.apply(ds) & JAWT_LOCK_ERROR()) != 0) {
                var freeDrawingSurface = jawt.FreeDrawingSurface(jawtt, SegmentScope.global());
                freeDrawingSurface.apply(ds);
                throw new RuntimeException("Failed to acquire awt lock");
            }
            var getDrawingSurfaceInfo = jawt_DrawingSurface.GetDrawingSurfaceInfo(ds, SegmentScope.global());
            var dsi = getDrawingSurfaceInfo.apply(ds);
            if (dsi.equals(MemorySegment.NULL)) {
                var unlock = jawt_DrawingSurface.Unlock(ds, SegmentScope.global());
                unlock.apply(ds);
                var freeDrawingSurface = jawt.FreeDrawingSurface(jawtt, SegmentScope.global());
                freeDrawingSurface.apply(ds);
                throw new RuntimeException("NULL drawing surface interface");
            }
            var platformInfo = jawt_DrawingSurfaceInfo.platformInfo$get(dsi);

            var hdc = jawt_Win32DrawingSurfaceInfo.hdc$get(platformInfo);
            if (hdc.equals(MemorySegment.NULL)) {
                throw new RuntimeException("something went seriously wrong");
            }
        }

    }

}
