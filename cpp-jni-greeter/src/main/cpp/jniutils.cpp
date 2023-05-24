
#include "com_example_greeter_JNIUtils.h"

/*
 * Class:     com_example_greeter_JNIUtils
 * Method:    makeGlobalRef
 * Signature: (Ljava/lang/Object;)J
 */
JNIEXPORT jlong JNICALL Java_com_example_greeter_JNIUtils_makeGlobalRef
        (JNIEnv *env, jclass, jobject object) {
    return (jlong) env->NewGlobalRef(object);
}

/*
 * Class:     com_example_greeter_JNIUtils
 * Method:    destroyGlobalRef
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_example_greeter_JNIUtils_destroyGlobalRef
        (JNIEnv *env, jclass, jlong object) {
    return env->DeleteGlobalRef((jobject) object);
}

/*
 * Class:     com_example_greeter_JNIUtils
 * Method:    readGlobalRef
 * Signature: (J)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_com_example_greeter_JNIUtils_readGlobalRef
        (JNIEnv *, jclass, jlong object) {
    return (jobject) object;
}

/*
 * Class:     com_example_greeter_JNIUtils
 * Method:    getEnv
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_example_greeter_JNIUtils_getEnv
        (JNIEnv *env, jclass) {
    return (jlong) env;
}
