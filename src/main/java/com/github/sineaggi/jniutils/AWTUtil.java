package com.github.sineaggi.jniutils;

import com.sineaggi.jniutils.internal.jni.*;

import java.awt.*;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.SegmentScope;
import java.util.function.Consumer;

import static com.sineaggi.jniutils.internal.jni.header_h.*;
import static com.sineaggi.jniutils.internal.jni.header_h.JAWT_LOCK_ERROR;

public class AWTUtil {
    private AWTUtil() {
        throw new AssertionError("No com.github.sineaggi.jniutils.AWTUtil instances for you!");
    }
    // todo: replace callback with FunctionalInterface
    public static void run(Component component, Consumer<MemorySegment> callback) {
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
            try {
                //System.out.printf("our DrawingSurface addr %016X\n", ds.address());
                var lock = jawt_DrawingSurface.Lock(ds, SegmentScope.global());
                if ((lock.apply(ds) & JAWT_LOCK_ERROR()) != 0) {
                    throw new RuntimeException("Failed to acquire awt lock");
                }
                var getDrawingSurfaceInfo = jawt_DrawingSurface.GetDrawingSurfaceInfo(ds, SegmentScope.global());
                var dsi = getDrawingSurfaceInfo.apply(ds);
                if (dsi.equals(MemorySegment.NULL)) {
                    throw new RuntimeException("NULL drawing surface interface");
                }
                try {
                    var platformInfo = jawt_DrawingSurfaceInfo.platformInfo$get(dsi);
                    callback.accept(platformInfo);
                } finally {
                    var unlock = jawt_DrawingSurface.Unlock(ds, SegmentScope.global());
                    unlock.apply(ds);
                }
            } finally {
                var freeDrawingSurface = jawt.FreeDrawingSurface(jawtt, SegmentScope.global());
                freeDrawingSurface.apply(ds);
            }
        }
    }
}
