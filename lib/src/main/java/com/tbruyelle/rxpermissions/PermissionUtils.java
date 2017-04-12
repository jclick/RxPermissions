package com.tbruyelle.rxpermissions;

import android.Manifest;

public class PermissionUtils {

    public static boolean reCheckPermissions(String permission){
        switch (permission){
            case Manifest.permission.ACCESS_COARSE_LOCATION:
            case Manifest.permission.ACCESS_FINE_LOCATION:
                return reCheckLocationPermission(permission);
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                return true;
            case Manifest.permission.CAMERA:
                return reCheckCameraPermission(permission);
        }
        return true;
    }

    private static boolean reCheckCameraPermission(String permission) {
    }

    private static boolean reCheckLocationPermission(String permission) {
        return false;
    }
}
