package com.gaurav.kyccamera.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;


public class CameraUtils {

    private static Camera camera;

    public static boolean hasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static Camera openCamera(Integer cameraFacing) {
        camera = null;
        try {
            if (cameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK){
                camera = Camera.open();
            } else {
                Integer cameraIndex = cameraFacing(cameraFacing);
                if (cameraIndex == null) {
                    camera = Camera.open();
                } else {
                    camera = Camera.open(cameraIndex);
                }
            }
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return camera; // returns null if camera is unavailable
    }

    private static Integer cameraFacing(Integer cameraFacing) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int cameraIndex = 0; cameraIndex < Camera.getNumberOfCameras(); cameraIndex++) {
            Camera.getCameraInfo(cameraIndex, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT
                    && cameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

                return cameraIndex;
            } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK
                    && cameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return cameraIndex;
            }
        }
        return null;
    }

    public static Camera getCamera() {
        return camera;
    }

    public static boolean hasFlash(Context context) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH) && parameters.getFlashMode() != null;
        }
        return false;
    }
}