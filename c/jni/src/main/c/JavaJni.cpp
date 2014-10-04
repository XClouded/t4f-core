/*
 * JavaJni.cpp
 */
/*
    private void zerojava(long x, long y) {
        while (y > 0) {
            long z = x;
            while (z > 0)
                z--;
            y--;
        }
    }
*/
#include "aos_jni_JavaJni.h"
#include  <iostream>

using namespace std;

JNIEXPORT void JNICALL Java_aos_jni_JavaJni_zerocpp(JNIEnv *env, jobject obj, jlong x, jlong y) {
	while(y > 0) {
		long z = x;
		while(z > 0) {
    		z--;
		}
		y--;
		cout << "Hello" << endl;
	}
}

int main() {
	return 0;
}
