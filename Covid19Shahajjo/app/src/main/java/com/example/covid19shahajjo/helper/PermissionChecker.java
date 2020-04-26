package com.example.covid19shahajjo.helper;

import android.Manifest;
import android.app.Activity;

import com.example.covid19shahajjo.utils.PermissionManager;

public class PermissionChecker {
    public final int ACCESS_FINE_LOCATION_CODE = 1001;
    public final int ACCESS_COARSE_LOCATION_CODE = 1002;
    public final int ACCESS_NETWORK_STATE_CODE = 1003;
    public final int INTERNET_CODE = 1004;
    public final int PHONE_CALL_CODE = 1005;

    public void requestFineLocationPermission(Activity activity){
        String[] permission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        PermissionManager.requestPermissions(activity, permission, ACCESS_FINE_LOCATION_CODE );
    }

    public void requestCoarseLocationPermission(Activity activity){
        String[] permission = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
        PermissionManager.requestPermissions(activity, permission, ACCESS_COARSE_LOCATION_CODE );
    }

    public void requestAccessNetworkStatePermission(Activity activity){
        String[] permission = new String[]{Manifest.permission.ACCESS_NETWORK_STATE};
        PermissionManager.requestPermissions(activity, permission, ACCESS_NETWORK_STATE_CODE );
    }

    public void requestInternetPermission(Activity activity){
        String[] permission = new String[]{Manifest.permission.INTERNET};
        PermissionManager.requestPermissions(activity, permission, INTERNET_CODE);
    }

    public void requestPhoneCallPermission(Activity activity){
        String[] permission = new String[]{Manifest.permission.CALL_PHONE};
        PermissionManager.requestPermissions(activity, permission, PHONE_CALL_CODE);
    }
}
