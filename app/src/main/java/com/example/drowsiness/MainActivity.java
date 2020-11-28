package com.example.drowsiness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    private static String TAG = "MainActivity";
    JavaCameraView javaCameraView;
    Mat mRGBa;
    BaseLoaderCallback mLoaderCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case BaseLoaderCallback.SUCCESS:{
                    System.loadLibrary("native-lib");
                    javaCameraView.enableView();
                    break;
                }
                default:{
                    super.onManagerConnected(status);
                    break;
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        javaCameraView = (JavaCameraView)findViewById(R.id.myCameraView);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);

    }
    @Override
    protected void onPause()
    {
        super.onPause();
        if(javaCameraView!=null)
        {
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(javaCameraView!=null)
        {
            javaCameraView.disableView();
        }
    }

    @Override
    protected void  onResume()
    {
        super.onResume();
        if(OpenCVLoader.initDebug())
        {
            Toast.makeText(getApplicationContext(), "Loaded successfully", Toast.LENGTH_LONG).show();
            mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Not Loaded successfully", Toast.LENGTH_LONG).show();
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this,mLoaderCallBack);
        }

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRGBa = new Mat(height,width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mRGBa.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRGBa = inputFrame.rgba();
        NativeClass.testFunction(mRGBa.getNativeObjAddr());
        return mRGBa;
    }
}