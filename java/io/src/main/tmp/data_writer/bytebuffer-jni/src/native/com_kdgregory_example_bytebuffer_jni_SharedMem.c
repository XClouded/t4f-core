#include <string.h>
#include <errno.h>
#include <sys/ipc.h>
#include <sys/shm.h>

#include "com_kdgregory_example_bytebuffer_jni_SharedMem.h"

/******************************************************************************
 ** Generally-useful functions for JNI programming.
 *****************************************************************************/

/**
 *  Throws a RuntimeException, with either an explicit message or the message
 *  corresponding to the current system error value.
 */
void throw(JNIEnv* env, const char* msg)
{
    if (msg == NULL)
        msg = sys_errlist[errno];

    jclass xklass = (*env)->FindClass(env, "java/lang/RuntimeException");
    (*env)->ThrowNew(env, xklass, msg);
}


/**
 *  Field ID lookup that throws if unable to find the field. Note that there is
 *  a cost for each ID lookup; if you're going to reference the field often,
 *  you should preserve the ID.
 */
jfieldID getMyFieldId(JNIEnv* env, jobject this, const char *name, const char *sig)
{
    jclass myClass = (*env)->GetObjectClass(env, this);
    jfieldID fieldId = (*env)->GetFieldID(env, myClass, name, sig);

    if (fieldId == NULL)
    {
        char* msg0 = "unable to retrieve my field: ";
        char msg[strlen(msg0) + strlen(name) + 1];
        strcpy(msg, msg0);
        strcat(msg, name);
        throw(env, msg);
    }

    return fieldId;
}


/******************************************************************************
 ** JNI implementations
 *****************************************************************************/

JNIEXPORT
void JNICALL Java_com_kdgregory_example_bytebuffer_jni_SharedMem_attach(
             JNIEnv* env,
             jobject this,
             jint shmKey,
             jint size)
{
    int shmId = shmget(shmKey, size, IPC_CREAT | 0777);
    if (shmId == -1)
        throw(env, NULL);

    void* shmAddr = shmat(shmId, NULL, 0);
    if (shmAddr == (void*)-1)
        throw(env, NULL);

    jobject buf = (*env)->NewDirectByteBuffer(env, shmAddr, size);
    if (buf == NULL)
        throw(env, "unable to allocate buffer");

    jfieldID fBuf = getMyFieldId(env, this, "_buf", "Ljava/nio/ByteBuffer;");
    (*env)->SetObjectField(env, this, fBuf, buf);
}


JNIEXPORT
void JNICALL Java_com_kdgregory_example_bytebuffer_jni_SharedMem_detach(
             JNIEnv* env,
             jobject this)
{
    jfieldID fBuf = getMyFieldId(env, this, "_buf", "Ljava/nio/ByteBuffer;");
    jobject buf = (*env)->GetObjectField(env, this, fBuf);
    if (buf == NULL)
        throw(env, "buffer reference is null");

    void* shmAddr = (void*)(*env)->GetDirectBufferAddress(env, buf);
    if (shmAddr == NULL)
        throw(env, "unable to retrieve buffer address");

    if (shmdt(shmAddr) == -1)
        throw(env, NULL);
}



