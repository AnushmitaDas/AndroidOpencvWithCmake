#include<opencv2/core.hpp>
#include <jni.h>
#include <opencv2/imgproc.hpp>

using namespace cv;
using namespace std;

extern "C"
JNIEXPORT void JNICALL Java_com_example_drowsiness_NativeClass_testFunction(JNIEnv *env, jclass type, jlong addrRgba)
{
    Mat &img= *(Mat *) addrRgba;
    cvtColor(img,img,COLOR_RGB2GRAY);
}

