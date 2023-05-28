
#include "com_github_sineaggi_jniutils_JNIUtils.h"

/*
 * Class:     com_github_sineaggi_jniutils_JNIUtils
 * Method:    makeGlobalRef
 * Signature: (Ljava/lang/Object;)J
 */
JNIEXPORT jlong JNICALL Java_com_github_sineaggi_jniutils_JNIUtils_makeGlobalRef
        (JNIEnv *env, jclass, jobject object) {
    return reinterpret_cast<jlong>(env->NewGlobalRef(object));
}

/*
 * Class:     com_github_sineaggi_jniutils_JNIUtils
 * Method:    destroyGlobalRef
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_sineaggi_jniutils_JNIUtils_destroyGlobalRef
        (JNIEnv *env, jclass, jlong object) {
    return env->DeleteGlobalRef(reinterpret_cast<jobject>(object));
}

/*
 * Class:     com_github_sineaggi_jniutils_JNIUtils
 * Method:    readGlobalRef
 * Signature: (J)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_com_github_sineaggi_jniutils_JNIUtils_readGlobalRef
        (JNIEnv *, jclass, jlong object) {
    return reinterpret_cast<jobject>(object);
}

/*
 * Class:     com_github_sineaggi_jniutils_JNIUtils
 * Method:    getEnv
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_sineaggi_jniutils_JNIUtils_getEnv
        (JNIEnv *env, jclass) {
    return reinterpret_cast<jlong>(env);
}
