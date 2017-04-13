package com.tbruyelle.rxpermissions;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.hardware.Camera;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Binder;
import android.os.Build;

public class PermissionUtils {

    public static boolean reCheckPermissions(Context context, String permission){
        if (context == null){
            return false;
        }
        switch (permission){
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                return reCheckCoarseLocationPermission(context);
            case Manifest.permission.ACCESS_FINE_LOCATION:
                return reCheckFineLocationPermission(context);
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                return true;
            case Manifest.permission.CAMERA:
                return reCheckCameraPermission(context);
        }
        return true;
    }

    private static boolean reCheckCameraPermission(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int checkOps = appOpsManager.checkOp(AppOpsManager.OPSTR_CAMERA, Binder.getCallingUid(), context.getPackageName());
            if (checkOps != AppOpsManager.MODE_ALLOWED){
                return false;
            }
        }
        try {
            Camera camera = Camera.open();
            camera.setParameters(camera.getParameters());
            camera.stopPreview();
            camera.release();
            camera = null;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private static boolean reCheckFineLocationPermission(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int checkOps = appOpsManager.checkOp(AppOpsManager.OPSTR_FINE_LOCATION, Binder.getCallingUid(), context.getPackageName());
            if (checkOps != AppOpsManager.MODE_ALLOWED){
                return false;
            }
        }
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            LocationProvider provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
            if (provider == null){
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private static boolean reCheckCoarseLocationPermission(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int checkOps = appOpsManager.checkOp(AppOpsManager.OPSTR_COARSE_LOCATION, Binder.getCallingUid(), context.getPackageName());
            if (checkOps != AppOpsManager.MODE_ALLOWED){
                return false;
            }
        }
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            LocationProvider provider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
            if (provider == null){
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
